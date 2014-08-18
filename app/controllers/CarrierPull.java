package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



















import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.CartonDtl;
import models.CartonHdr;
import models.CartonInquiry;
import models.FilterCriteria;
import models.ItemMaster;
import models.OutbdLoad;
import models.RGHICarrierPull;
import models.RGHICarrierPullPK;
import models.ShipVia;
import play.*;
import play.mvc.*;
import views.html.*;
import play.api.libs.json.Json;
import play.api.libs.json.Writes;
import play.db.DB;
import play.db.jpa.*;

public class CarrierPull extends Controller {
	
	// @Transactional
    public static Result index() {
    	
		return ok(index.render("RGH v0.1"));
		
    }
  
    
    /**
     * Server-side actions.
     */
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getCarrierPull() throws JsonParseException, JsonMappingException, IOException {
    	System.out.println("getCarrierPull()");
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get UI params from POST JSON body	
    	JsonNode json = request().body().asJson();
    	int limit = json.get("pageSize").asInt();
    	int page = json.get("page").asInt();
    	
    	// Set up basic query on CartonHdr
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
    	CriteriaQuery<RGHICarrierPull> cq = cb.createQuery(RGHICarrierPull.class);
    	List<Predicate> predicateList = new ArrayList<Predicate>();
    	Root<RGHICarrierPull> hdr = cq.from(RGHICarrierPull.class);
    	cq.select(hdr);
    	//Predicate whereClause = cb.equal(cb.literal(1), 1);
    	//cq.where(whereClause);
    	
    	/**
    	 * Examples of filter criteria
    	 */

    	// Apply any search criteria (filters) from the UI
    	List<JsonNode> results = json.findValues("filters");  // Json node called filters
    	Iterator<JsonNode> it = results.iterator();  // Get ready to iterate them
    	ArrayNode filterCriteriaEntries = new ObjectMapper().createArrayNode();  // Cast as ArrayNode TODO : VA : cant we just initialize this to NULL? why create a new object?
    	while (it.hasNext()) {
    		// VC: For some reason, ExtJs is serializing this JSON array 
    		// as a string, so instead of getting multiple values here we
    		// get one string.  So we must take this first element, cast
    		// it again from string to ArrayNode using Json, to get the actual elements.
    		JsonNode node = it.next();
    		String node_string = node.asText();
    		filterCriteriaEntries = (ArrayNode)play.libs.Json.parse(node_string);
    	}
    	// Process each of the filter criteria, and apply them to the query expression
    	// (or not)
    	for (JsonNode filter:filterCriteriaEntries) {  // Now loop over them as JsonNode
    		// Example:  {'property':'carton_nbr', 'value': '00000999990005080020'}
    		String prop = filter.get("property").asText();
    		String val  = filter.get("value").asText();
    		// In case the UI returns an empty filter if the user removed
    		// an existing filter value,for now, ignore these
    		// (searching for nulls may come later)
    		if (!val.equals("")){ 
    			// Add this to the where clause
    			CarrierPull.evalSearchCriteria(predicateList, hdr, prop, val);
    		}
    	}
    	
    	// Add the predicates from the UI filters
    	Predicate[] predicates = new Predicate[predicateList.size()];
        predicates=predicateList.toArray(predicates);
        cq.where(predicates);
    	
    	TypedQuery<RGHICarrierPull> records = JPA.em().createQuery(cq);
    	records.setFirstResult((page-1)*limit);
    	records.setMaxResults(limit);

    	// Execute query
    	List<RGHICarrierPull> lst = records.getResultList();
//    	for (int i=0; i < lst.size(); i++) {
//    		System.out.println("whse = " + lst.get(i).getWhse() + ", shipToZip = " + lst.get(i).getShiptoZip() + ", shipVia = " + lst.get(i).getShipVia().getShipVia());
//    	}
    	retval.put("data", play.libs.Json.toJson(lst));
    	
    	// Get the record count
    	CriteriaQuery<Long> cq_recCount = cb.createQuery(Long.class);
    	Root<RGHICarrierPull> r = cq_recCount.from(RGHICarrierPull.class);
    	cq_recCount.select(cb.count(r));  // add the count
    	cq_recCount.where(predicates);      // add the filter criteria
    	
    	TypedQuery<Long> q = JPA.em().createQuery(cq_recCount);  // create a new query object
    	Long total_rows = q.getSingleResult(); // get the count
    	retval.put("totalrows",total_rows);  // add total to the json
    	
    	return ok(retval);
    }
    
    public static Boolean evalSearchCriteria(List<Predicate> predicateList, Root<RGHICarrierPull> root, String prop, String val) {
    	Boolean success=true;
    	String fieldName = "";
    	
    	System.out.println("Processing search criteria: " + prop + " = " + val);
    	
    	// Get the actual field name (ie: strip out from_, to_, etc)
    	if (prop.startsWith("from_")) {
    		fieldName = prop.substring(5);
    	}
    	else if (prop.startsWith("to_")) {
    		fieldName = prop.substring(3);
    	}
    	else {
    		fieldName = prop;
    	}
    	
    	// Tokenize the fieldName to handle dot notation
    	String[] fieldNameParts=fieldName.split("\\.");
    	
    	// If there's dot notation, camel-case the first character
    	if (fieldNameParts.length <=1) {
    		System.out.println("ERROR: invalid search field definition from UI: " + prop + "   .  Must contain at least TableName.FieldName in field definition.");
    		//TODO : VA : Need to throw an Exception here
    	}
    	else if (fieldNameParts.length >= 2) {
    		// TODO: The recursive approach, not finished yet
    	    RGH.applyQueryPart(fieldNameParts, predicateList, root, val);
    		
    		// TODO: The manual approach to parsing fieldNameParts, based on length of array
    		
    		String v = Character.toString(fieldName.charAt(0)).toLowerCase() + fieldName.substring(1);
    		fieldName=v;
    	}
    	
    	/**
    	 * Note from VC: to/from is currently disabled in CarrierPull, since the
    	 * previous code won't work, and CarrierPull doesn't have any from/to.
    	 * Someone will need to update this from/to code to use the new approach.
    	 */
//    	// Apply from/to fields to the expression
//    	if (prop.startsWith("from_")) {
//    		query.where(cb.ge(query.get, val));  
////    		.ge(fieldName, val);
//    		System.out.println("\tadded expression: " + fieldName + " >= " + val);
//    	}
//    	else if (prop.startsWith("to_")) {
//    		query.where().le(fieldName, val);
//    		System.out.println("\tadded expression: " + fieldName + " <= " + val);
//    	}
//    	// Apply all other fields to the expression
//    	else {
//    		query.where().eq(fieldName, val);
//    		System.out.println("\tadded expression: " + fieldName + " = " + val);
//    	}
    	return success;
    }
    
    /****************************
     * Handle requests from UI
     ****************************/
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getShipVias() throws Exception {
    	//ObjectNode retval = play.libs.Json.newObject();
		
    	//Create the Query
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
    	CriteriaQuery<ShipVia> cq = cb.createQuery(ShipVia.class);
    	Root<ShipVia> r = cq.from(ShipVia.class);
    	cq.select(r);
    	cq.orderBy(cb.asc(r.get("shipVia")));
    	
    	//Run the Query
    	TypedQuery<ShipVia> query = JPA.em().createQuery(cq);
    	List<ShipVia> shipViaList = query.getResultList();
    	
    	//retval.put("data", play.libs.Json.toJson(shipViaList));
    	return ok(play.libs.Json.toJson(shipViaList));
    	
    }
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result deleteCarrierPull() throws JsonParseException, JsonMappingException, IOException {
//    	System.out.println("getCarrierPull()");
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get UI params from POST JSON body	
    	JsonNode json = request().body().asJson();
    	
    	// Get the records to delete from the JSON
    	List<JsonNode> results = json.findValues("records");  // records to be deleted
    	ArrayNode recsToDelete = new ObjectMapper().createArrayNode();  // Cast as ArrayNode
    	Iterator<JsonNode> it = results.iterator();  // Get ready to iterate them
    	recsToDelete = (ArrayNode)results.get(0);
    	Boolean success=true;
    	// Loop over the records to delete
    	for (JsonNode rec : recsToDelete) {
    		// TODO:  Delete each record
    		// Note:  the UI assumes that either:
    		//     - all records are deleted, and success = true
    		//     - if any records failed to delete, the whole transaction
    		//       was rolled back and success=false was sent back to UI
    		//       and the error message was sent back in the json in
    		//       message: "Could not delete record because..."
//    		System.out.println("about to delete record: " + rec.get("shipVia"));
    		String q = "Select c FROM RGHICarrierPull c WHERE c.pk.shipVia=:via AND c.pk.shipToZip=:zip AND c.pk.whse=:whse";
    		TypedQuery<RGHICarrierPull> query = JPA.em().createQuery(q,RGHICarrierPull.class);
    		
    		query.setParameter("zip", rec.get("shipToZip").asText());
    		query.setParameter("via", rec.get("shipVia").asText());
    		query.setParameter("whse", "OH1");
    		RGHICarrierPull result = (RGHICarrierPull)query.getSingleResult();
//    		RGHICarrierPull entityToDel = play.libs.Json.fromJson(rec, RGHICarrierPull.class);
    		JPA.em().remove(result);
    		
    		
    		success=true;	
    		// If any delete failed, roll back 
    		if (success.equals(false)) {
    			retval.put("success", "false");
    			retval.put("message", "Error msg from failed SQL delete goes here...\nLine 2\nLine 3");
    		}
    		else {
    			retval.put("success", "true");
    		}
    	}
    	
    	
    	
    	return ok(retval);
    }
    
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result deleteAllCarrierPullForWarehouse() throws JsonParseException, JsonMappingException, IOException {
//    	System.out.println("getCarrierPull()");
    	Boolean success = false;
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get UI params from POST JSON body	
    	JsonNode json = request().body().asJson();
//    	int limit = json.get("pageSize").asInt();
    	
    	// TODO:  Write query for deleting all carrier pulls
    	// todo:  set "success" variable depending on whether
    	//        query succeeds or not
    	success=true;
    	if (success) {
    		retval.put("success", "true");
    		retval.put("message", "All records for this warehouse were deleted.");
    	}
    	else {
    		retval.put("success", "false");
    		retval.put("message", "(Describe the error that happened here...)");
    	}
    	
    	return ok(retval);
    }
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    //public static Result saveCarrierPull() throws JsonParseException, JsonMappingException, IOException {
    public static Result saveCarrierPull() throws Exception {
    	System.out.println("in saveCarrierPull()");
    	EntityManager em = JPA.em();
    	Boolean success = false;
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get the incoming record as JSON and Entity
    	JsonNode recJson = request().body().asJson();
    	//RGHICarrierPull recEntity = play.libs.Json.fromJson(recJson, RGHICarrierPull.class);
    	
    	String isNew="";
    	if (recJson.get("isNew")!=null) {
    		isNew=recJson.get("isNew").asText();
    	}
    	
    	
    	// See if we're editing a new record or editing an existing one
    	if (isNew.equals("true")) {
    		try {
	    		// We are inserting a new record
	    		RGHICarrierPull recEntity = new RGHICarrierPull();
	    		
	    		// Set Default fields
	    		recEntity.setCreateDateTime(new Date());
	    		recEntity.setModDateTime(new Date());
	    		
	    		// Set the Primary Key Values
	    		recEntity.setWhse("OH1");
	    		recEntity.setShipToZip(recJson.get("shipToZip").asText());
	    		
	    		//Get the ShipVia Object
	    		//Create the Query
	        	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
	        	CriteriaQuery<ShipVia> cq = cb.createQuery(ShipVia.class);
	        	Root<ShipVia> r = cq.from(ShipVia.class);
	        	cq.select(r);
	        	cq.where(cb.equal(r.get("shipVia"), recJson.get("shipVia").asText()));
	        	
	        	//Run the Query
	        	TypedQuery<ShipVia> query = JPA.em().createQuery(cq);
	        	ShipVia shipViaObj = query.getSingleResult();
	        	
	        	recEntity.setShipVia(shipViaObj);
	        	
	        	//Set the Optional Values
	        	if (recJson.get("pullTrlrCode")!=null) {
	        		recEntity.setPullTrlrCode(recJson.get("pullTrlrCode").asText());
	        	}
	        	if (recJson.get("pullTime")!=null) {
	        		recEntity.setPullTime(recJson.get("pullTime").asText());
	        	}
	        	if (recJson.get("pullTimeAMPM")!=null) {
	        		recEntity.setPullTimeAMPM(recJson.get("pullTimeAMPM").asText());
	        	}
	        	if (recJson.get("anyText1")!=null) {
	        		recEntity.setAnyText1(recJson.get("anyText1").asText());
	        	}
	        	if (recJson.get("anyNbr1")!=null) {
	        		recEntity.setAnyNbr1(recJson.get("anyNbr1").asLong());
	        	}
	    		    		
	    		em.persist(recEntity);
	    		em.flush();
	    		success = true;
	    		System.out.println("New record saved: " + recJson);
    		}
    		catch (Exception e) {
    			System.out.println("VCO Add Exception : " + e.toString());
    			success = false;
    			retval.put("success", "false");
        		retval.put("message", e.toString());
        		return ok(retval);
    		}
    	}
    	else { // We're editing an existing record.
    		try {
	    		// Look up the existing record
	    		String q = "Select c FROM RGHICarrierPull c WHERE c.shipVia.shipVia = :via AND c.shipToZip = :zip AND c.whse = :whse";
	    		TypedQuery<RGHICarrierPull> query = JPA.em().createQuery(q, RGHICarrierPull.class);
	    		
	    		query.setParameter("zip", recJson.get("shipToZip").asText());
	    		query.setParameter("via", recJson.get("shipVia").asText());
	    		query.setParameter("whse", "OH1");
	    		RGHICarrierPull rghiCarrierPull = (RGHICarrierPull)query.getSingleResult();
	    		
	    		if (rghiCarrierPull != null) {  // we found the record to update in db
	    			// Set Default fields
	    			rghiCarrierPull.setModDateTime(new Date());
	    			
	    			
	    			//Set the Optional Values
	            	if (recJson.get("pullTrlrCode")!=null) {
	            		rghiCarrierPull.setPullTrlrCode(recJson.get("pullTrlrCode").asText());
	            	}
	            	if (recJson.get("pullTime")!=null) {
	            		rghiCarrierPull.setPullTime(recJson.get("pullTime").asText());
	            	}
	            	if (recJson.get("pullTimeAMPM")!=null) {
	            		rghiCarrierPull.setPullTimeAMPM(recJson.get("pullTimeAMPM").asText());
	            	}
	            	if (recJson.get("anyText1")!=null) {
	            		rghiCarrierPull.setAnyText1(recJson.get("anyText1").asText());
	            	}
	            	if (recJson.get("anyNbr1")!=null) {
	            		rghiCarrierPull.setAnyNbr1(recJson.get("anyNbr1").asLong());
	            	}
	    			
	    			// update the record in db
	    			JPA.em().persist(rghiCarrierPull);
	    			em.flush();
	    			success = true;
	    			System.out.println("Record updated.");
	    		}
	    		else {
	    			System.out.println("Record Lookup failed.");
	    			success = false;
	    			retval.put("success", "false");
	        		retval.put("message", "Record Lookup failed for Whse = " + "OH1" + ", ShipVia = " + recJson.get("shipVia").asText() + ", shipToZip = " + recJson.get("shipToZip").asText());
	        		return ok(retval);
	    		}
    		}
    		catch (Exception e) {
    			System.out.println("VCO Edit Exception : " + e.toString());
    			success = false;
    			retval.put("success", "false");
        		retval.put("message", e.toString());
        		return ok(retval);
    		}    		
    	}
    	
    	// Here's an example of converting the incoming JSON to an in-memory object,
    	// and saving that object as a record in the db
    	//RGHICarrierPull rec = play.libs.Json.fromJson(jsonToSave, RGHICarrierPull.class);
    	//JPA.em().persist(rec);
    
    	if (success) {
    		retval.put("success", "true");
    		retval.put("message", "Record was saved.");
    	}
    	else {
    		retval.put("success", "false");
    		retval.put("message", "There was a problem saving your record");
    	}
    	
    	return ok(retval);
    }
    
}