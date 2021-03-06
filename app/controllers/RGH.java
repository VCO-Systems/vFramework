package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
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

public class RGH extends Controller {
	
//	 @Transactional
    public static Result index() {
    	 
		return ok(index.render("RGH v0.1"));
		
    }
    
    @SuppressWarnings("unchecked")
	@Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getCartons() throws JsonParseException, JsonMappingException, IOException {
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get UI params from POST JSON body	
    	JsonNode json = request().body().asJson();
    	int limit = json.get("pageSize").asInt();
    	int page = json.get("page").asInt();
    	
    	// Set up basic query on CartonHdr
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
    	CriteriaQuery cq = cb.createQuery();
    	Root<CartonHdr> hdr = cq.from(CartonHdr.class);
    	Predicate whereClause = cb.equal(cb.literal(1), 1);
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
    			evalSearchCriteria(cq, hdr, prop,val);
    		}
    	}
    	
    	// Get record count
//    	Query count_query = cb.createQuery();//.createQuery(cq);
    	cq.select(cb.count(hdr));
    	cq.where(whereClause);
    	TypedQuery<Long> q = JPA.em().createQuery(cq);
    	Long total_rows = q.getSingleResult();
    	
    	// Paginate
    	cq.select(hdr);
    	cq.where(whereClause);
    	TypedQuery<CartonHdr> records = JPA.em().createQuery(cq);
    	records.setFirstResult((page-1)*limit);
    	records.setMaxResults(limit);

    	// Execute query
    	List<CartonHdr> lst = records.getResultList();
    	retval.put("totalrows",total_rows);
    	retval.put("data", play.libs.Json.toJson(lst));
    	
    	return ok(retval);
    }
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getCartonsOld() throws JsonParseException, JsonMappingException, IOException {
    	ObjectNode retval = play.libs.Json.newObject();
    	// VC: Use this to get params from POST JSON body	
    	JsonNode json = request().body().asJson();
    	int limit = json.get("pageSize").asInt();
    	int page = json.get("page").asInt();
    	
    	// Set up basic query
    	@SuppressWarnings("unchecked")
    	Query cartonDtlQuery = JPA.em().createQuery("select hdr FROM CartonHdr hdr WHERE hdr.carton_nbr = '00000999990001369860' order by hdr.carton_nbr");
//    	Query cartonDtlQuery = JPA.em().createQuery("select hdr FROM CartonHdr hdr WHERE hdr.carton_nbr = '00000999990001369860' order by hdr.carton_nbr");
    	
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
    	CriteriaQuery cq = cb.createQuery();
    	
    	Root ch = cq.from(CartonHdr.class);
//    	Join<CartonDtl,OutbdLoad> ld = ch.join("load");
//    	cq.where(cb.equal(ch.get("load.load_nbr"), "LD000001842"));
    	
    	
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
    			evalSearchCriteria(cq, ch, prop,val);
    		}
    	}
    	
    	
    	Query q = JPA.em().createQuery(cq);
    	
//    	// Get total record count
//    	List<CartonHdr> cartonHdrList = cartonDtlQuery.getResultList();
//    	int totalRows = cartonHdrList.size();
    	
    	// Apply pagination
    	q.setMaxResults(limit);
    	q.setFirstResult((page-1)*limit);
//    	cartonHdrList = q.getResultList();
    	
    	
    	/**
    	 * Check for search criteria in "filters" object
    	 */
    	
    	/**
    	
    	
    	// Add order by
//    	cartons_expr=cartons_expr.orderBy("carton_nbr");
    	
    	// Build the paging list
    	PagingList<CartonDtl> carton_dtl_pl = cartons_expr	
    		.findPagingList(Integer.parseInt(limit));
    	// Get the total row count
    	int totalRows = carton_dtl_pl.getTotalRowCount();
    	// Get the requested page
    	List<CartonDtl> data = carton_dtl_pl
    			// ExtJs starts pageNum at 1, eBean starts at 0, so adjust here
    			.getPage(Integer.parseInt(page)-1) 
    			.getList();
    		
    	// Flatten these WMOS records into a list of CartonInquiry objects for the UI
    	List<CartonInquiry> cartonInquiryList = convertToCartonInquiry(data);

    	*/
    	
    	// Construct the return JSON object
    	retval.put("success", "true");
//    	retval.put("totalrows", totalRows);
//    	retval.put("data", play.libs.Json.toJson(cartonHdrList));
    	
    	return ok(retval);
    }
    
    public static List<CartonInquiry> convertToCartonInquiry(List<CartonDtl> carton_dtls) {
    	List<CartonInquiry> retval = new ArrayList<CartonInquiry>();
    	
    	// Iterate the carton_hdr records
    	Iterator<CartonDtl> cartons = carton_dtls.iterator();
    	while (cartons.hasNext()) {
    		CartonDtl carton = cartons.next();
    		CartonInquiry inq = new CartonInquiry();

    		// carton_dtl fields
//    		inq.carton_nbr       = carton.
//    		inq.carton_seq_nbr   = carton.pk.carton_seq_nbr;
    		inq.to_be_pakd_units = carton.to_be_pakd_units;
    		inq.units_pakd       = carton.units_pakd;
    		
    		// carton_hdr fields
    		inq.pkt_ctrl_nbr     = carton.cartonHdr.pkt_ctrl_nbr;
    		inq.whse             = carton.cartonHdr.whse;
    		inq.wave_nbr         = carton.cartonHdr.wave_nbr;
    		inq.carton_creation_code = carton.cartonHdr.carton_creation_code;
    		
    		// item_master fields
    		inq.style           = carton.itemMaster.style;
    		inq.style_sfx       = carton.itemMaster.style_sfx;
    		inq.sku_brcd        = carton.itemMaster.sku_brcd;
    		inq.sku_desc        = carton.itemMaster.sku_desc;
    		inq.size_desc       = carton.itemMaster.size_desc;
    		inq.color           = carton.itemMaster.color;
    		inq.color_sfx       = carton.itemMaster.color_sfx;
    		inq.sec_dim         = carton.itemMaster.sec_dim;
    		inq.qual            = carton.itemMaster.qual;
    		inq.season          = carton.itemMaster.season;
    		inq.season_yr       = carton.itemMaster.season_yr;
    		
    		retval.add(inq);
    	}
    	return retval;
    }
    
    /**
     * Parse and apply a single search criteria (from the user) for an existing Ebean expressionList
     * 
     * @param query  The pending query that criteria should be added to.
     * @param prop The name of the property to search on
     * @param val  The value to match
     * @return
     * @throws ClassNotFoundException 
     */
    
    public static Boolean evalSearchCriteria(CriteriaQuery query, Root root, String prop, String val) {
    	Boolean success=true;
    	String baseClassName="CartonHdr";  // The default model to search on is CartonDtl
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
//    	    RGH.applyQueryPart(fieldNameParts, query, root, val);
    		
    		// TODO: The manual approach to parsing fieldNameParts, based on length of array
    		
    		
    		// Verify that this property exists on this entity
//    		cb.like(arg0, arg1) 
    		
    		StringBuilder tmpFieldName = new StringBuilder(fieldName);
    		String v = Character.toString(fieldName.charAt(0)).toLowerCase() + fieldName.substring(1);
    				//Character.toLowerCase(fieldName.indexOf(0)) + fieldName.substring(1);
    		fieldName=v;
    	}
    	
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
    
    public static String[] applyQueryPart(String[] fieldDef, List<Predicate> predicateList, Root<RGHICarrierPull> root, String criteria) {
    	CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
    	// If we're down to Table.Field, apply it to the query
    	// Example:  CartonHdr.carton_nbr
    	if (fieldDef.length == 2) {
    		System.out.println("\tApplying filter criteria : " +  fieldDef[0] + "." + fieldDef[1]);
    		Predicate newPred = null;
    		if (fieldDef[1].equals("shipVia")) {
    			System.out.println("Adding criteria for shipVia");
    			newPred = cb.like(root.get(fieldDef[1]).get(fieldDef[1]).as(String.class), criteria + "%");
    		}
    		else {
    			newPred = cb.like(root.get(fieldDef[1]).as(String.class), criteria + "%");
    		}
    		predicateList.add(newPred);
    	}
    	// Otherwise, continue to traverse
    	else {
    		System.out.println("\tTraversing.  Looking up property: " + fieldDef[0] + "." + fieldDef[1]);
    		// If this property is a OneToMany, add a join to the query
    		// TODO: determine what type of join is present for fieldDef[1]
    		
    	}
    	
    	return fieldDef;
    }
    
    /**
     * Server-side actions.
     */
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result printCartonLabels() throws JsonParseException, JsonMappingException, IOException {
    	
    	ObjectNode retval = play.libs.Json.newObject();
    	
    	// Get the json data from the UI
    	JsonNode json = request().body().asJson();
    	if (json==null) {
    		System.out.println("ERROR: expecting json data");
    	}
    	
    	// Get the list of carton_nbrs the user selected
    	List<JsonNode> records = json.findValues("records");
    	// VC: Play is seeing an extra array inside this array,
    	// which doesn't show up in the raw JSON data,
    	// so reach into records and get rec
    	JsonNode rec = records.get(0);
    	Iterator<JsonNode> it = rec.iterator();
    	// Loop over the records
    	while(it.hasNext()) {
    		JsonNode carton_info = it.next();
    		String carton_nbr = carton_info.get("carton_nbr").asText();
    		System.out.println(carton_nbr);
    	}
    	
    	retval.put("success", "true");
    	return ok(retval);
    }
    
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getDBSchema() throws JsonParseException, JsonMappingException, IOException, SQLException {
    	ObjectNode retval = play.libs.Json.newObject();
    	// Get UI params from POST JSON body	
//    	JsonNode json = request().body().asJson();
//    	int limit = json.get("pageSize").asInt();
//    	int page = json.get("page").asInt();
    	
    	// Set up basic query
    	Connection conn = DB.getConnection();
    	DatabaseMetaData databaseMetaData = conn.getMetaData();
    	String productName    = databaseMetaData.getDatabaseProductName();
    	String productVersion = databaseMetaData.getDatabaseProductVersion();
    	retval.put("productName", productName);
    	retval.put("productVersion", productVersion);
    	
    	// Get info on a entity class
    	
    	
//    	CriteriaQuery cq = cb.
//    	retval.put("data", play.libs.Json.toJson(lst));
    	return ok(retval);
    }
    
    
}
