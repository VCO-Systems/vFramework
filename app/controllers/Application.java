package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import play.*;
import play.mvc.*;
import views.html.*;
import play.api.libs.json.Json;
import play.api.libs.json.Writes;
import play.db.DB;
import play.db.jpa.*;

public class Application extends Controller {
	
	// @Transactional
    public static Result index() {
    	//List<CartonHdr> op = CartonHdr.getAll();
    	//return ok(index.render("Your new application is ready."));
    	
		return ok(index.render("RGH v0.1"));
		
    }
  
    
    /**
     * Server-side actions.
     */
    
    
    
}