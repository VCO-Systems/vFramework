package controllers;

import java.util.List;
import java.util.Map;

import models.CartonHdr;
import play.*;
import play.mvc.*;
import views.html.*;
import play.db.jpa.*;

public class Application extends Controller {
	
	// @Transactional
    public static Result index() {
    	//List<CartonHdr> op = CartonHdr.getAll();
    	//return ok(index.render("Your new application is ready."));
		
		return ok(index.render(""));
		
    }
    
    public static Result getCartons() {
    	String j = CartonHdr.find.all().size() + " carton header records.";
    	return ok(j);
    }
}
