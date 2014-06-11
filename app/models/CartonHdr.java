package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="carton_hdr")
public class CartonHdr extends Model{

    
    //public Long id;
    @Id
    public String carton_nbr;
    public String pkt_ctrl_nbr;
    public String whse;
    public Long seq_rule_prty;
    public Long stat_code;
    public Long stage_indic;
    public String sngl_sku_flag;
    public String sku_id;
    public Long carton_creation_code;
    public String pick_locn_id;
    public String curr_locn_id;
    public String prev_locn_id;
    public String dest_locn_id;
    public String alt_carton_nbr;
    public String spur_lane;
    public String spur_posn;
    public String first_zone;
    public String last_zone;
    public Long nbr_of_zones;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Generic query helper for entity Company with id Long
     */
    public static Model.Finder<Long,CartonHdr> find = new Model.Finder<Long,CartonHdr>(Long.class, CartonHdr.class);

    
	
    
    
    
    
    /**
	 * 
	 * 
	 * NON-EBEAN METHODS
	 * (commented out by VC, don't delete)
	 * 
 	public static CartonHdr findById(Long id) {
        return JPA.em().find(CartonHdr.class, id);
    }
    
    public static Map<String,String> options() {
        @SuppressWarnings("unchecked")
				List<CartonHdr> cartonHdrs = JPA.em().createQuery("from CartonHdr order by carton_nbr").getResultList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(CartonHdr c: cartonHdrs) {
            options.put(c.id.toString(), c.carton_nbr);
            System.out.println(c.carton_nbr);
        }
        return options;
    }
    
    //public static List<CartonHdr> page(int page, int pageSize, String sortBy, String order, String filter) {
    public static List<CartonHdr> getAll() {
    	//List<CartonHdr> ch = JPA.em().createQuery("select * from CartonHdr").getResultList();
    	Query q = JPA.em().createQuery("select * from carton_hdr");
    	@SuppressWarnings("unchecked")
		List<CartonHdr> ch = q.getResultList();
    	for(CartonHdr c: ch) {
    		System.out.println(c.carton_nbr);
    	}
    	return ch;
    }
    */

}