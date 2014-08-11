package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    	//List<CartonHdr> op = CartonHdr.getAll();
    	//return ok(index.render("Your new application is ready."));
    	
		return ok(index.render("RGH v0.1"));
		
		
    }
  
    
    /**
     * Server-side actions.
     */
    
    @SuppressWarnings("unchecked")
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
    	CriteriaQuery cq = cb.createQuery();
    	List<Predicate> predicateList = new ArrayList<Predicate>();
    	Root<RGHICarrierPull> hdr = cq.from(RGHICarrierPull.class);
    	Predicate whereClause = cb.equal(cb.literal(1), 1);
    	cq.select(hdr);
    	cq.where(whereClause);
//    	cq.select(hdr);
    	
    	// Join CartonDtl
//    	Join dtl = hdr.join("cartonDtls");
    	
    	/**
    	 * Examples of filter criteria
    	 */
    	
    	// CartonHdr.carton_nbr
//    	cq.where(cb.equal(hdr.get("carton_nbr"), "00000999990001369860"));
    	
    	// CartonDtls.units_pakd (via OneToMany)
    	
    	// Apply any search criteria (filters) from the UI
    	List<JsonNode> results = json.findValues("filters");  // Json node called filters
    	Iterator<JsonNode> it = results.iterator();  // Get ready to iterate them
    	ArrayNode filterCriteriaEntries = new ObjectMapper().createArrayNode();  // Cast as ArrayNode
    	while (it.hasNext()) {
    		// VC: For some reason, ExtJs is serializing this JSON array 
    		// as a string, so instead of getting multiple values here we
    		// get one string.  So we must take this first element, cast
    		// it again from string to ArrayNode using Jackson, to get the actual elements.
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
    			CarrierPull.evalSearchCriteria(predicateList, hdr, prop,val);
    		}
    	}
    	
    	// Add the predicates from the UI filters
    	Predicate[] predicates = new Predicate[predicateList.size()];
        predicates=predicateList.toArray(predicates);
        cq.where(predicates);
    	
    	// Get record count
//    	Query count_query = JPA.em().createQuery(cq);//.createQuer  (cq);//.createQuery(cq);
//    	
//    	.select(cb.count(hdr));
//    	cq.where(whereClause);
//    	TypedQuery<Long> q = JPA.em().createQuery(cq);
//    	Long total_rows = q.getSingleResult();
    	
    	// Return the records
//    	cq.select(hdr);
//    	cq.where(whereClause);
    	TypedQuery<RGHICarrierPull> records = JPA.em().createQuery(cq);
    	records.setFirstResult((page-1)*limit);
    	records.setMaxResults(limit);

    	// Execute query
    	List<RGHICarrierPull> lst = records.getResultList();
    	retval.put("data", play.libs.Json.toJson(lst));
    	
    	// Get the record count
    	cq.select(cb.count(hdr));  // add the count
    	cq.where(predicates);      // add the filter criteria
    	TypedQuery<Long> q = JPA.em().createQuery(cq);  // create a new query object
    	Long total_rows = q.getSingleResult(); // get the count
    	retval.put("totalrows",total_rows);  // add total to the json
    	
//    	TypedQuery<Long> q = JPA.em().createQuery(cq);
//    	Long total_rows = q.getSingleResult();
    	
    	return ok(retval);
    }
    
    public static Boolean evalSearchCriteria(List<Predicate> predicateList, Root root, String prop, String val) {
    	Boolean success=true;
    	String baseClassName="RGHICarrierPull";  // The default model to search on is CartonDtl
    	String fieldName,fieldType="";
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
//		try {
//			Class cl = Class.forName("models.CartonDtl");
//		} catch (ClassNotFoundException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
    	
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
    	}
    	else if (fieldNameParts.length >= 2) {
    		// TODO: The recursive approach, not finished yet
    	    RGH.applyQueryPart(fieldNameParts, predicateList, root, val);
    		
    		// TODO: The manual approach to parsing fieldNameParts, based on length of array
    		
    		
    		// Verify that this property exists on this entity
//    		cb.like(arg0, arg1) 
    		
    		StringBuilder tmpFieldName = new StringBuilder(fieldName);
    		String v = Character.toString(fieldName.charAt(0)).toLowerCase() + fieldName.substring(1);
    				//Character.toLowerCase(fieldName.indexOf(0)) + fieldName.substring(1);
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
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getShipVias() {
    	ObjectNode retval = play.libs.Json.newObject();
    	
    	// Get the json data from the UI
    	JsonNode json = request().body().asJson();
    	if (json==null) {
    		System.out.println("ERROR: expecting json data");
    	}
    	
    	// Get the list of carton_nbrs the user selected
//    	List<JsonNode> records = json.findValues("records");
    	
    	// For now, this list is hard-coded so UI development
    	// can continue.
    	// TODO: replace this with proper DB query
    	List<JsonNode> lst = new ArrayList();
    	lst.add(play.libs.Json.parse("{\"id\":\"E07\",\"text\":\"E07\"}"));
    	lst.add(play.libs.Json.parse("{\"id\":\"E08\",\"text\":\"E08\"}"));
    	lst.add(play.libs.Json.parse("{\"id\":\"E09\",\"text\":\"E09\"}"));
    	lst.add(play.libs.Json.parse("{\"id\":\"E10\",\"text\":\"E10\"}"));
    	
    	return ok(play.libs.Json.toJson(lst));
    }
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result deleteCarrierPull() throws JsonParseException, JsonMappingException, IOException {
    	System.out.println("getCarrierPull()");
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
    		System.out.println("about to delete record: " + rec.get("shipVia"));
    		success=false;	
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
    
}