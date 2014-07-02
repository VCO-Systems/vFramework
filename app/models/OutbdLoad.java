package models;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="outbd_load")
public class OutbdLoad extends Model {
    
	@Id
	public String load_nbr;
	
//	private CartonHdr carton;
//	
//	@JsonIgnore
//	@ManyToOne
//    @JoinColumn(name="load_nbr")
//	public CartonHdr getCarton() {
//		return carton;
//	}
//	
//	public void setCartonHdr(CartonHdr hdr) {
//		carton=hdr;
//	}
	
	public String whse;
	public String ship_via;
	
//    @ManyToOne
//    public CartonHdr carton;
    
    
    private static final long serialVersionUID = 14L;
    
    /**
     * Generic query helper for entity OutbdLoad with id Long
     */
    public static Model.Finder<Long,OutbdLoad> find = new Model.Finder<Long,OutbdLoad>(Long.class, OutbdLoad.class);

}