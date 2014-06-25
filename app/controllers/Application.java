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

import models.CartonHdr;
import models.FilterCriteria;
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
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Handle to/from fields
    	if (prop.startsWith("from_")) {
    		ebean_expression.where().gt(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " >= " + val);
    	}
    	if (prop.startsWith("to_")) {
    		ebean_expression.where().lt(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " <= " + val);
    	}
    	
    	return success;
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
    	
//    	JsonNode j = json.get("filters");
//    	System.out.println("filters as JsonNode: " + j);
//    	ArrayNode j_arr = (ArrayNode)j;
//    	List<FilterCriteria> filt_list = new ArrayList<FilterCriteria>();
//    	Iterator<JsonNode> it = j_arr.iterator();
//    	
//    	while (it.hasNext()) {
//    		JsonNode node = it.next();
//    		String prop = node.get("property").asText();
//    		System.out.println(prop);
//    	}
    	
    	// carton_nbr
//    	ObjectMapper mapper = new ObjectMapper();
//    	Map<String,String> mp = mapper.readValue(json.findPath("filters").asText(),
//    			new TypeReference<Map<String,String>>(){});
//    	System.out.println("Map.tostring: " + mp.toString());
//    	Iterator<JsonNode> filters = json.findPath("filters").elements();///.findValues("filters");
//    	System.out.println("filter count: ");
    	
//    	FilterCriteria[] lstFilters = new Array<FilterCriteria>();
//    	try {
//			lstFilters = mapper.readValue(json.findPath("filters").asText(),FilterCriteria[].class);
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	System.out.println(lstFilters.length);
    	
//    	while(filters.hasNext()) {
//    		
//    		JsonNode filter = filters.next();
//    		
//    		System.out.println(filter.asText());
//    		String p = filter.findPath("property").toString();
//			System.out.println(p);
//    	}
    	
    	
    	
//    	if (filters.size() >= 0) {
//    		for (JsonNode filter:filters) {
//    			
//    			System.out.println(filter);
//    			
////    			ObjectMapper mapper = new ObjectMapper();
////    			List<FilterCriteria> prop = mapper.readValue(filters.as,mapper.getTypeFactory().constructCollectionType(List.class, FilterCriteria.class));//filter.findValue("property").asText();
////    			String val = filter.findValue("value").asText();
////    			System.out.println(prop + " / " + val);
//    		}
    		
//    	}
    	
    	// Build the initial Ebean query expression
    	ExpressionList<CartonHdr> cartons_expr = CartonHdr.find.where();
    	
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
    	
//    	System.out.println("Row Count: " + cartons.getTotalRowCount());
//    	
//    	List<CartonHdr> page_of_cartons = cartons
//    			.getPage(Integer.parseInt(page))
//    			.getList();
//    	System.out.println("Results on page " + page + ": " + page_of_cartons.size());
    	
    	// 2nd approach to querying
//    	List<CartonHdr> new_cartons = Ebean.find(CartonHdr.class)
//    			.where()
//    			.like("carton_nbr", "0000099999000132%")
//    			.findPagingList(Integer.parseInt(limit))
//    			.getPage(Integer.parseInt(page))
//    			.getList();
    	
    	// RAW SQL approach to querying
//    	Query<CartonHdr> query = Ebean.find(CartonHdr.class);
//    	String sql = "SELECT * from carton_hdr ORDER BY carton_hdr";
//    	RawSql rawSql = RawSqlBuilder.parse(sql);
//    	PagingList pl = query.setRawSql(arg0)
    			
    	
    	
    	// Construct the return JSON object
    	ObjectNode retval = play.libs.Json.newObject();
    	retval.put("success", "true");
    	retval.put("totalrows", totalRows);
    	retval.put("data", play.libs.Json.toJson(data));
    	
    	return ok(retval);
    }
    
    
}
