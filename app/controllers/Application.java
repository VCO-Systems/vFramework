package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.CartonHdr;
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
    
    public static Result getCartons() {

    	/**
    	 * This paging approaches gets paging params as querystrings,
    	 * and uses ebean's PagingList to perform the queries.
    	 */
    	
    	// Get paging params from the url
    	JsonNode json = request().body().asJson();
    	String limit = request().getQueryString("limit");
    	String page = request().getQueryString("page");
    	
    	// Use the paging params to request a page
    	List<CartonHdr> page_of_cartons = CartonHdr.find.where()
    			.orderBy("carton_nbr")
    			.findPagingList(Integer.parseInt(limit))
    			.getPage(Integer.parseInt(page))
    			.getList();
    	ObjectMapper mapper = new ObjectMapper();
    	String js="";
    	
    	try {
    		js=mapper.writeValueAsString(page_of_cartons);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ok(js);
    }
}
