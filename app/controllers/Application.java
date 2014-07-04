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
    			.fetch("itemMaster")
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
     * @param ebean_expression  The pending query that criteria should be added to.
     * @param prop The name of the property to search on
     * @param val  The value to match
     * @return
     * @throws ClassNotFoundException 
     */
    
    public static Boolean evalSearchCriteria(ExpressionList ebean_expression, String prop, Object val) {
    	Boolean success=true;
    	String className="CartonDtl";  // The default model to search on is CartonDtl
    	String fieldName,fieldType="";
    	
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
    	
    	
    	/**
    	 * VC: Commenting out the section of code for now, but do not
    	 * delete it.  This is for looking up the requesting Class.Field
    	 * in the app models, to confirm it exists and get its type for 
    	 * validation.  We will probably need to do this, but right now 
    	 * we just pass it straight through to the query from the
    	 * Field.name property in the UI field.
    	 */
    	
//    	// Make sure that field exists on the model,
//    	// and get its type
//    	try {
////    		System.out.println((Class) fieldName.getType()).getName());
//    		
//    		// If the fieldname has dot notation, it's:  Classname.fieldname
//    		
//			
//    		String[] field_parts = fieldName.split("\\.");
//    		System.out.println("--- " + field_parts.length);
//    		if (field_parts.length==2) {
//    			
//    			className=field_parts[0];
//    			fieldName=field_parts[1];
//    			System.out.println("\tsearch field with dot notation: " + className + "." + fieldName);
//    			try {
//					// Look up this Class in Play Framework's models package
//    				cl = Class.forName("models." + className);
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} //className.getClass();
//    		}
//    		else {
//    			// Search fields are assumed to be on CartonDtl,
//    			// if not specified.
//    			System.out.println("\tsearch field without dot notation.  treating as fieldname.");
//    		}
//    		System.out.println("Filtering on " + className + "." + fieldName + " = " + val);
//    		
//    		java.lang.reflect.Field fld = cl.getField(fieldName);
//			if (fld != null) {
//				String fqdn = fld.getType().toString().split(" ")[1];
//				String[] fqdn_parts = fqdn.split("\\.");
//				fieldType = fqdn_parts[fqdn_parts.length-1];
//				System.out.println("\tfield type: " + fieldType);
//			}
//		} catch (NoSuchFieldException e) {
//			// TODO Auto-generated catch block
//			success=false;
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			success=false;
//			e.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		};
    	
    	
    	// convert "CartonHdr.carton_nbr" to "cartonHdr.carton_nbr"
    	// because the "CartonHdr" capitalization allows us to use
    	// Java reflection to validate against the model classes,
    	// but in the query, we must use the name of the One-to-one
    	// property added to the model, which by convention is in 
    	// camel-case.
    	
    	// If there's dot notation, camel-case the first character
    	if (fieldName.split("\\.").length>0) {
    		StringBuilder tmpFieldName = new StringBuilder(fieldName);
    		String v = Character.toString(fieldName.charAt(0)).toLowerCase() + fieldName.substring(1);
    				//Character.toLowerCase(fieldName.indexOf(0)) + fieldName.substring(1);
    		fieldName=v;
    	}
    	
    	// Apply from/to fields to the expression
    	if (prop.startsWith("from_")) {
    		ebean_expression.where().ge(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " >= " + val);
    	}
    	else if (prop.startsWith("to_")) {
    		ebean_expression.where().le(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " <= " + val);
    	}
    	// Apply all other fields to the expression
    	else {
    		ebean_expression.where().eq(fieldName, val);
    		System.out.println("\tadded expression: " + fieldName + " = " + val);
    	}
    	return success;
    }
    
    
}
