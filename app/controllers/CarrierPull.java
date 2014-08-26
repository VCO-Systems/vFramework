package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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






























import org.apache.http.cookie.Cookie;
import org.w3c.dom.Document;

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
import models.WebSession;
import play.*;
import play.mvc.*;
import play.mvc.BodyParser.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;
import play.api.libs.json.Json;
import play.api.libs.json.Writes;
import play.db.DB;
import play.db.jpa.*;

public class CarrierPull extends Controller {
	
	@Transactional
    public static Result index() {
    	/**
   	  * When app first loads, get sessionid from url param,
   	  * use it to look up session info (warehouse, userid)
   	  * and store those in cookies.
   	  */
		// Clear cookies from previous session
//		response().discardCookie("user_id");
//		response().discardCookie("warehouse");
//		response().setCookie("warehouse","");
//		request().
//		session().clear();
		
		
		
		 // Get session ID from request url
	   	String sid = request().getQueryString("sessionid");
	   	System.out.println("Looking up sessionid: " + sid);
	   	// Look up session in web_session table
	   	TypedQuery<WebSession> query =
	   	    JPA.em().createNamedQuery("WebSession.findById", WebSession.class);
	   	query.setParameter("sessId", Long.parseLong(sid));
	   	List<WebSession> results = query.getResultList();
	   	
	   	// If that session exists
	   	if (results.size() == 1) {
	   		WebSession sess = results.get(0);
//	   		response().setCookie("user_id","");
	   		
	   		Iterator<play.mvc.Http.Cookie> cooks = response().cookies().iterator();
			while(cooks.hasNext()) {
				play.mvc.Http.Cookie c = cooks.next();
				System.out.println("Cookie before setting: " + c.name() + " / " + c.value());
				if (c.name().equals("user_id")) {
//					c.value()="jf";
				}
			}
			
	   		System.out.println("setting user_id to: " + sess.getLoginUserId());
	   		response().setCookie("user_id", sess.getLoginUserId(),null);
	   		
	   		// Get the SESSION_XML
	   		Document xml = play.libs.XML.fromString(sess.getSessionXml());
	   		// Get whse and userid from xml
	   		String whse = xml.getElementsByTagName("warehouse").item(0).getTextContent();
	//   		session("whse",whse);
//	   		response().discardCookie("warehouse");
	   		System.out.println("setting cookies: " + whse + " / " + sess.getLoginUserId());
	   		response().setCookie("warehouse", whse,null);
	   		
	   		System.out.println("XML: "  + xml.getElementsByTagName("warehouse").item(0).getAttributes().getNamedItem("id"));
	   	}
	   	else {
	   		System.out.println("Session not found.");
	   	}
	   	// Add session id to session cookie
		return ok(index.render(""));
		
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
    	
    	List<Predicate> predicateList = new ArrayList<Predicate>();
    	
    	// Set up basic query on CartonHdr
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
    	CriteriaQuery<RGHICarrierPull> cq = cb.createQuery(RGHICarrierPull.class);
    	Root<RGHICarrierPull> from = cq.from(RGHICarrierPull.class);
    	CriteriaQuery<RGHICarrierPull> select = cq.select(from);
    	
    	
    	// Prepare the predicate to limit to current warehouse, but don't apply to query yet
    	String whse = request().cookie("warehouse").value();
    	Predicate whse_pred = cb.equal(from.get("whse"), whse);
    	predicateList.add(whse_pred);
    	
    	/**
    	 * Get any filter criteria from UI, construct
    	 * predicates from them, but don't apply to query yet
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
    			CarrierPull.evalSearchCriteria(predicateList, from, prop, val);
    		}
    	}
    	
    	// Prepare the predicates to be added to the query
    	Predicate[] predicates = new Predicate[predicateList.size()];
        predicates=predicateList.toArray(predicates);
    	
        /**
         * Get the total count of records for this query.
         */
    	CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    	countQuery.select(cb.count(from));  // add the count
    	JPA.em().createQuery(countQuery);
    	countQuery.where(predicates);
    	Long total_rows = JPA.em().createQuery(countQuery).getSingleResult();
    	retval.put("totalrows",total_rows);  // add total to the json
    	
    	/**
    	 * Get the actual page of data.
    	 */
    	select.where(predicates);
    	select.orderBy(cb.asc(from.get("shipVia").get("shipVia")), cb.asc(from.get("shipToZip")));
    	TypedQuery<RGHICarrierPull> records = JPA.em().createQuery(select);
    	records.setFirstResult((page-1)*limit);
    	records.setMaxResults(limit);
    	List<RGHICarrierPull> lst = records.getResultList();
    	retval.put("data", play.libs.Json.toJson(lst));
    	
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
    //public static Result deleteCarrierPull() throws JsonParseException, JsonMappingException, IOException {
    public static Result deleteCarrierPull() throws Exception {
    	System.out.println("deleteCarrierPull()");
    	Boolean success=false;
    	Long nbr_of_recs_deleted = 0L;
    	String shipVia = "";
    	String shipToZip = "";
    	String whse = "";
    	EntityManager em = JPA.em();
    	ObjectNode retval = play.libs.Json.newObject();
    	
    	try {
	    	// Get UI params from POST JSON body	
	    	JsonNode json = request().body().asJson();
	    	
	    	// Get the records to delete from the JSON
	    	List<JsonNode> results = json.findValues("records");  // records to be deleted
	    	ArrayNode recsToDelete = new ObjectMapper().createArrayNode();  // Cast as ArrayNode
	    	//Iterator<JsonNode> it = results.iterator();  // Get ready to iterate them
	    	recsToDelete = (ArrayNode)results.get(0);
	  
	    	// Loop over the records to delete
	    	for (JsonNode rec : recsToDelete) {
	    		// TODO:  Delete each record
	    		// Note:  the UI assumes that either:
	    		//     - all records are deleted, and success = true
	    		//     - if any records failed to delete, the whole transaction
	    		//       was rolled back and success=false was sent back to UI
	    		//       and the error message was sent back in the json in
	    		//       message: "Could not delete record because..."
	    		//System.out.println("about to delete record: " + rec.get("shipVia"));
	    		String q = "Select c FROM RGHICarrierPull c WHERE c.shipVia.shipVia = :via AND c.shipToZip = :zip AND c.whse = :whse";
	    		TypedQuery<RGHICarrierPull> query = JPA.em().createQuery(q, RGHICarrierPull.class);
	    		
	    		whse = rec.get("whse").asText();
	    		shipToZip = rec.get("shipToZip").asText();
	    		shipVia = rec.get("shipVia").asText();
	    		
	    		query.setParameter("zip", shipToZip);
	    		query.setParameter("via", shipVia);
	    		query.setParameter("whse", whse);
	    		RGHICarrierPull result = (RGHICarrierPull)query.getSingleResult();
	    		//RGHICarrierPull entityToDel = play.libs.Json.fromJson(rec, RGHICarrierPull.class);
	    		em.remove(result);
	    		em.flush();
	    		nbr_of_recs_deleted += 1;
	    		
	    	}
    	}
    	catch (Exception e) {
    		success = false;
			retval.put("success", "false");
    		retval.put("message", nbr_of_recs_deleted.toString() + " Records Deleted.\n Delete failed for Whse = " + "OH1" + ", ShipVia = " + shipVia + ", shipToZip = " + shipToZip + "\nException : " + e.toString());
    		return ok(retval);
    	}
    	
		success=true;	
		retval.put("success", "true");
		retval.put("message", nbr_of_recs_deleted.toString() + " Records Deleted");
    	retval.put("total_records", nbr_of_recs_deleted);
		return ok(retval);
    }
    
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result deleteAllCarrierPullForWarehouse() throws JsonParseException, JsonMappingException, IOException {
    	System.out.println("deleteAllCarrierPullForWarehouse()");
    	EntityManager em = JPA.em();
    	Boolean success = false;
    	int nbr_of_recs_deleted = 0;
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get UI params from POST JSON body	
    	JsonNode json = request().body().asJson();
//    	int limit = json.get("pageSize").asInt();
    	
    	try {
    	
    	String q = "Delete FROM RGHICarrierPull c WHERE c.whse = :whse";
		TypedQuery<RGHICarrierPull> query = JPA.em().createQuery(q, RGHICarrierPull.class);
		
		query.setParameter("whse", "OH1"); //hardcoded for now
		nbr_of_recs_deleted = query.executeUpdate();
		
		em.flush();
    	}
    	catch (Exception e) {
    		success=false;	
    		retval.put("success", "false");
    		retval.put("message", "Exception deleting records for Whse.\n Exception : " + e.toString());
    		return ok(retval);
    	}
    	
		success=true;	
		retval.put("success", "true");
		retval.put("message", nbr_of_recs_deleted + " Records Deleted for Whse");
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
	    		recEntity.setUserId(request().cookie("user_id").value());
	    		
	    		// Set the Primary Key Values
	    		recEntity.setWhse(request().cookie("warehouse").value());
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
	        	if (recJson.get("anyNbr1")!=null && recJson.get("anyNbr1").asText()!="") {
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
	    		query.setParameter("whse", request().cookie("warehouse").value());
	    		RGHICarrierPull rghiCarrierPull = (RGHICarrierPull)query.getSingleResult();
	    		
	    		if (rghiCarrierPull != null) {  // we found the record to update in db
	    			// Set Default fields
	    			rghiCarrierPull.setModDateTime(new Date());
	    			rghiCarrierPull.setUserId(request().cookie("user_id").value());
	    			
	    			
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
	            	if (recJson.get("anyNbr1")!=null && recJson.get("anyNbr1").asText()!="") {
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
    
    /**
     * User has uploaded a CSV file from the "Import" UI option.
     * @throws Exception 
     */
    
    @Transactional
	public static Result uploadCSV() throws Exception {
		ObjectNode retval = play.libs.Json.newObject();
		int linesProcessed=0;
		
		EntityManager em = JPA.em();

		String whse = request().cookie("warehouse").value();
		String userId = request().cookie("user_id").value();
		
		System.out.println("Whse = "+ whse);
		System.out.println("User = "+ userId);
		
		// Get the CSV file from the uploaded form
		play.mvc.Http.MultipartFormData body = request().body()
				.asMultipartFormData();
		FilePart uploadedCSV = body.getFile("document");
		
		
		if (uploadedCSV != null) 
		{

			String fileName = uploadedCSV.getFilename();
			retval.put("filename", fileName);
			
			File file = uploadedCSV.getFile();
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			try 
			{

				    String[] columns;

				    String shipToZip = "";
			    	String shipVia = "";
			    	String pullTrlrCode = "";
			    	String pullTime = "";
			    	String pullTimeAMPM = "";
			    	String anyText1 = "";
			    	long anyNumber1 = 0;
			    	

				    while (in.ready()) 
				    {  

					    shipToZip = "";
				    	shipVia = "";
				    	pullTrlrCode = "";
				    	pullTime = "";
				    	pullTimeAMPM = "";
				    	anyText1 = "";
				    	anyNumber1 = 0;
				    	
				    	String s = in.readLine();
				    	columns = s.split(",");
				    	
			    		if (!columns[0].equals(whse))
			    		{
			    			System.out.println("Whse - " + columns[0] + " is not same as User's Whse - " + whse);
			    			System.out.println(s);
			    			continue;
			    		}

			    		for (int i = 0; i < columns.length; i++)
			    		{
			    			if (i == 0)
			    				whse = columns[i];
			    			else if (i == 1)
						    	shipToZip = columns[i];
			    			else if (i == 2)
			    				shipVia = columns[i];
			    			else if (i == 3)
			    				pullTrlrCode = columns[i];
			    			else if (i == 4)
			    				pullTime = columns[i];
			    			else if (i == 5)
			    				pullTimeAMPM = columns[i];
			    			else if (i == 6)
			    				anyText1 = columns[i];
			    			else if (i == 7)
			    				anyNumber1 = Long.parseLong(columns[i]);
			    			
			    		}
				    	
				    	RGHICarrierPull rcp = loadRGHICarrierPull(whse, shipToZip, shipVia);
				    	if (rcp == null)
				    	{
				    		rcp = createRGHICarrierPull
						    		(
						    		   whse,
						    		   shipToZip,
						    		   shipVia,
						    		   pullTrlrCode,
						    		   pullTime,
						    		   pullTimeAMPM,
						    		   anyText1,
						    		   anyNumber1,
						    		   userId
						    		);
				    	}
				    	else
				    	{
				    		rcp.setPullTrlrCode(pullTrlrCode);
				    		rcp.setPullTime(pullTime);
				    		rcp.setPullTimeAMPM(pullTimeAMPM);
				    		rcp.setAnyText1(anyText1);
				    		rcp.setAnyNbr1(anyNumber1);
				    		rcp.setModDateTime(new Date());
				    		
				    	}
				    	
				    	em.persist(rcp);
				    	
				    	linesProcessed++;
				    }
				
				    in.close();
				    retval.put("success", "true");
				

			} 
			catch (Exception e) 
			{

				in.close();
				e.printStackTrace();
				retval.put("success", "false");
				retval.put("message", e.getMessage());
				
			}
			
			// Prepare the response
			retval.put("linesProcessed", linesProcessed);
			response().setContentType("text/html");
			
			// Send success msg to ui
			return ok(retval);
		} 
		else 
		{
			// Send error msg to UI
			retval.put("success", "false");
			retval.put("message", "Server did not receive valid CSV file to process.");
			return ok(retval);
		}
	}
	
	public static Result exportCSV() {
		String downloadFilename="test.csv";
		
		// Construct CSV contents using StringBuilder
		StringBuilder csv = new StringBuilder();
		csv.append("col1,col2,col3\n");
		csv.append("1,a,b\n");
		
		// Prepare the response
		response().setContentType("text/csv");
		response().setHeader("Content-Disposition", "attachment;filename="+downloadFilename);
		return ok(csv.toString()); 
		}


	private static RGHICarrierPull loadRGHICarrierPull 
	(
	   String whse,
	   String shipToZip,
	   String shipVia
	)
	throws Exception
	{
		RGHICarrierPull rcp = null;
		try
		{
			String q = "Select c FROM RGHICarrierPull c WHERE c.shipVia.shipVia = :shipVia AND c.shipToZip = :shipToZip AND c.whse = :whse";
			TypedQuery<RGHICarrierPull> query = JPA.em().createQuery(q, RGHICarrierPull.class);
			
			query.setParameter("shipToZip", shipToZip);
			query.setParameter("shipVia", shipVia);
			query.setParameter("whse", whse);
			rcp = (RGHICarrierPull)query.getSingleResult();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in loadRGHICarrierPull - " + e.toString());
			//throw();
		}
		
		return rcp;
	}
	
	private static RGHICarrierPull createRGHICarrierPull
	(
	   String whse,
	   String shipToZip,
	   String shipVia,
	   String pullTrlrCode,
	   String pullTime,
	   String pullTimeAMPM,
	   String anyText1,
	   long anyNumber1,
	   String userId
	)
	throws Exception
	{
		RGHICarrierPull rcp = new RGHICarrierPull();

		try
		{
    		
    		rcp.setWhse(whse);
    		rcp.setShipToZip(shipToZip);
    		rcp.setShipVia(loadShipVia(shipVia));
    		rcp.setPullTrlrCode(pullTrlrCode);
    		rcp.setPullTime(pullTime);
    		rcp.setPullTimeAMPM(pullTimeAMPM);
    		rcp.setAnyText1(anyText1);
    		rcp.setAnyNbr1(anyNumber1);
    		rcp.setUserId(userId);
    		rcp.setCreateDateTime(new Date());
    		rcp.setModDateTime(new Date());
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in createRGHICarrierPull - " + e.toString());
		}
		
		return rcp;
	}

	private static ShipVia loadShipVia
	(
	   String shipVia
	)
	{

		ShipVia sv = null;
		
		try
		{
        	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
        	CriteriaQuery<ShipVia> cq = cb.createQuery(ShipVia.class);
        	Root<ShipVia> r = cq.from(ShipVia.class);
        	cq.select(r);
        	cq.where(cb.equal(r.get("shipVia"), shipVia));
        	
        	TypedQuery<ShipVia> query = JPA.em().createQuery(cq);
        	sv = query.getSingleResult();
		}
		catch(Exception e)
		{
			System.out.println("Exception in loadShipVia - " + e.toString());
		}
		
		return sv;	
		
	}
	
	
}

