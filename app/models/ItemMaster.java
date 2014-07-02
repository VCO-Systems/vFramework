package models;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="item_master")
public class ItemMaster extends Model {
    
	
//	private CartonDtl cartonDtl;
//	
//	// Many to One to carton_dtl
//	@JsonIgnore
//	@ManyToOne
//    @JoinColumn(name="sku_id")
//	public CartonDtl getCartonDtl() {
//		return cartonDtl;
//	}
//	
//	public void setCartonDtl(CartonDtl dtl) {
//		cartonDtl=dtl;
//	}
	
	
	
	
	
	@Id
	public String sku_id;
	
	
	
	
	public String season;
	public String season_yr;
	
	
//	@ManyToOne
//	@JoinColumn(name="sku_id", referencedColumnName="sku_id")
//	public CartonDtl cartondtl;
	
	
	
//	public CartonHdr getCartonHdr() {
//		return cartonHdr;
//	}
//	
//	public void setCartonHdr(CartonHdr hdr) {
//		cartonHdr=hdr;
//	}
	
    
    
   
    
    
    private static final long serialVersionUID = 13L;
    
    /**
     * Generic query helper for entity ItemMaster with id Long
     */
    public static Model.Finder<Long,ItemMaster> find = new Model.Finder<Long,ItemMaster>(Long.class, ItemMaster.class);

}