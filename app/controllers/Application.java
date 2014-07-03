package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
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
import play.*;
import play.mvc.*;
import views.html.*;
import play.api.libs.json.Json;
import play.api.libs.json.Writes;
import play.db.jpa.*;

public class Application extends Controller {
	
	// @Transactional
    public static Result index() {
    	//List<CartonHdr> op = CartonHdr.getAll();
    	//return ok(index.render("Your new application is ready."));
		
		return ok(index.render(""));
		
    }
    
    

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getCartons() throws JsonParseException, JsonMappingException, IOException {

    	/**
    	 * This paging approaches gets paging params as querystrings,
    	 * and uses ebean's PagingList to perform the queries.
    	 */
    	
    	// Get paging params from the url
    	

    	// VC: Use this to get params from GET query string
//    	String limit = request().getQueryString("limit");
//    	String page = request().getQueryString("page");
    	
    	// VC: Use this to get params from POST JSON body
    	JsonNode json = request().body().asJson();
    	String limit = json.get("pageSize").asText();
    	String page = json.get("page").asText();

    	// Use the paging params to request a page
    	
//    			.orderBy("carton_nbr");
//    				.like("carton_nbr", "00000999990001%");
    	
    	/**
    	 * Check for search criteria in "filters" object
    	 */
    	
    	
    	// Build the initial Ebean query expression
    	ExpressionList<CartonHdr> cartons_expr = CartonHdr.find
    			.fetch("cartonDtls")
    			.where();
    	
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
//    			cartons_expr.where().like(prop, val);
    			evalSearchCriteria(cartons_expr,prop,val);
    		}
    	}
    	
    	// Add order by
//    	cartons_expr=cartons_expr.orderBy("carton_nbr");
    	
    	// Build the paging list
    	PagingList<CartonHdr> cartons = cartons_expr	
    		.findPagingList(Integer.parseInt(limit));
    	// Get the total row count
    	int totalRows = cartons.getTotalRowCount();
    	// Get the requested page
    	List<CartonHdr> data = cartons
    			// ExtJs starts pageNum at 1, eBean starts at 0, so adjust here
    			.getPage(Integer.parseInt(page)-1) 
    			.getList();
    	
    	// Convert these WMOS objects into a CartonInquiry model
//    	convertToCartonInquiry(data);
    	
    	// Construct the return JSON object
    	ObjectNode retval = play.libs.Json.newObject();
    	retval.put("success", "true");
    	retval.put("totalrows", totalRows);
    	retval.put("data", play.libs.Json.toJson(data));
    	
    	return ok(retval);
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getCartonInquiry() throws JsonParseException, JsonMappingException, IOException {

    	// VC: Use this to get params from POST JSON body
    	JsonNode json = request().body().asJson();
    	String limit = json.get("pageSize").asText();
    	String page = json.get("page").asText();

    	/**
    	 * Check for search criteria in "filters" object
    	 */
    	
    	
    	// Build the initial Ebean query expression
    	ExpressionList<CartonDtl> cartons_expr = CartonDtl.find
    			.fetch("item")
    			.where()
    			;
    	
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
//    			cartons_expr.where().like(prop, val);
    			evalSearchCriteria(cartons_expr,prop,val);
    		}
    	}
    	
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
    	
    	// Construct the return JSON object
    	ObjectNode retval = play.libs.Json.newObject();
    	retval.put("success", "true");
    	retval.put("totalrows", totalRows);
    	retval.put("data", play.libs.Json.toJson(cartonInquiryList));
    	
    	//
    	System.out.println("Total rows: " + totalRows);
    	
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
    		inq.carton_nbr       = carton.pk.carton_nbr;
    		inq.carton_seq_nbr   = carton.pk.carton_seq_nbr;
    		inq.to_be_pakd_units = carton.to_be_pakd_units;
    		inq.units_pakd       = carton.units_pakd;
    		
    		// carton_hdr fields
    		inq.pkt_ctrl_nbr     = carton.cartonHdr.pkt_ctrl_nbr;
    		inq.whse             = carton.cartonHdr.whse;
    		inq.wave_nbr         = carton.cartonHdr.wave_nbr;
    		
    		// item_master fields
    		inq.style           = carton.item.style;
    		inq.style_sfx       = carton.item.style_sfx;
    		inq.sku_brcd        = carton.item.sku_brcd;
    		inq.sku_desc        = carton.item.sku_desc;
    		
    		retval.add(inq);
    	}
    	return retval;
    }
    
    /**
     * Example code for querying and filtering on One-to-Many,
     * One-to-One relationships in existing tables that cannot
     * be modified.
     * 
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result testRelationships() throws JsonParseException, JsonMappingException, IOException {
    	// The return value, using the "display model" CartonInquiry
    	CartonInquiry retval = new CartonInquiry();
    	// Parse the request body as JSON
    	JsonNode json = request().body().asJson();
    	
    	// Set up the basic query on carton_hdr
    	ExpressionList<CartonHdr> cartons_expr = CartonHdr.find
    			.fetch("cartonDtls")
    			.where()
//    				.eq("carton_nbr","00000999990001369860")
    			;
    	// Apply paging params
    	PagingList<CartonHdr> cartons = cartons_expr	
        		.findPagingList(60);
    	List<CartonHdr> data = cartons
    			// ExtJs starts pageNum at 1, eBean starts at 0, so adjust here
    			.getPage(0) 
    			.getList();
    	
    	// Debugging info
    	System.out.println("----");
    	System.out.println("Carton hdrs found: " + cartons.getTotalRowCount());
    	
    	// Iterate the CARTON_HDR records
    	Iterator<CartonHdr> carton_hdrs = data.iterator();
    	while (carton_hdrs.hasNext()) {
    		
    		// Fields from carton_dtl
    		CartonHdr cd = carton_hdrs.next();
    		System.out.println("Carton nbr: " + cd.carton_nbr);
    		retval.carton_nbr     = cd.carton_nbr;
//    		retval.carton_seq_nbr = cd.carton_seq_nbr;
//    		retval.units_pakd     = cd.units_pakd;
    		retval.pkt_ctrl_nbr   = cd.pkt_ctrl_nbr;
    		
    		// Get carton_dtl records
    		List<CartonDtl> dtls = cd.getCartonDtls();
//    		retval.carton_dtls    = dtls;
    		System.out.println("\tCarton dtls: " + dtls.size());
    		
    		// Get outbd_load records
    		OutbdLoad ld = cd.load;
    		System.out.println("\tLoad.load_nbr: " + ld.load_nbr);
//    		retval.outbd_load   = ld;
    		System.out.println("\tLoad ship_via: " + ld.ship_via);
    		
    		// Get item_master fields
//    		retval.season   = ld
    		
    	}
    	
    	
    	return ok(play.libs.Json.toJson(retval));
    }
    
    /**
     * Example of querying items, and using relationships to get cartons, etc
     * 
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result testItems() throws JsonParseException, JsonMappingException, IOException {
    	// The return value, using the "display model" CartonInquiry
    	CartonInquiry retval = new CartonInquiry();
    	
    	// Parse the request body as JSON
    	JsonNode json = request().body().asJson();
    	
    	// Set up the basic query on carton_hdr
    	ExpressionList<ItemMaster> items_expr = ItemMaster.find
//    			.fetch("outbdLoads")
    			.where()
//    				.eq("carton_nbr","00000999990001369860")
    			;
    	// Apply paging params
    	PagingList<ItemMaster> items = items_expr	
        		.findPagingList(60);
    	
    	System.out.println("Items found: " + items.getTotalRowCount());
    	List<ItemMaster> data = items
    			// ExtJs starts pageNum at 1, eBean starts at 0, so adjust here
    			.getPage(0) 
    			.getList();
    	
    	// Set up an iterator on the carton hdrs
    	Iterator<ItemMaster> items_iter = data.iterator();
//    	while (items_iter.hasNext()) {
//    		retval.items.add(items_iter.next());
//    		
//    	}
    	
    	return ok(play.libs.Json.toJson(retval));
    }
    /**
     * Parse and apply a single search criteria (from the user) for an existing Ebean expressionList
     * 
     * @param ebean_expression
     * @param prop
     * @param val
     * @return
     */
    
    public static Boolean evalSearchCriteria(ExpressionList ebean_expression, String prop, Object val) {
    	Boolean success=true;
    	String fieldName,fieldType="";
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
    	
    	// Make sure that field exists on the model,
    	// and get its type
    	try {
			java.lang.reflect.Field fld = CartonHdr.class.getField(fieldName);
			if (fld != null) {
				String fqdn = fld.getType().toString().split(" ")[1];
				String[] fqdn_parts = fqdn.split("\\.");
				fieldType = fqdn_parts[fqdn_parts.length-1];
				System.out.println("\tfield type: " + fieldType);
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			success=false;
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			success=false;
			e.printStackTrace();
		}
    	
    	// Apply from/to fields to the expression
    	if (prop.startsWith("from_")) {
    		ebean_expression.where().gt(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " >= " + val);
    	}
    	else if (prop.startsWith("to_")) {
    		ebean_expression.where().lt(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " <= " + val);
    	}
    	// Apply all other fields to the expression
    	else {
    		ebean_expression.where().eq(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " <= " + val);
    	}
    	
    	return success;
    }
    
    
}
