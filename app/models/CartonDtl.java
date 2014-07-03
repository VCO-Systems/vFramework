package models;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="carton_dtl")
public class CartonDtl extends Model {
    
	
	@Embeddable
	public class CartonNbrKey implements Serializable {
		public String carton_nbr;
		public Long carton_seq_nbr;
		
		public int hashCode() {
	        return (int) carton_nbr.hashCode() + carton_nbr.length();//+ carton_seq_nbr;
	    }

	    public boolean equals(Object obj) {
	        if (obj == this) return true;
	        if (!(obj instanceof CartonNbrKey)) return false;
	        if (obj == null) return false;
	        CartonNbrKey pk = (CartonNbrKey) obj;
	        return pk.carton_seq_nbr == carton_seq_nbr && pk.carton_nbr.equals(carton_nbr);
	    }
	}
	
	@Id
	public CartonNbrKey pk;
	
//	public String carton_nbr;
	
	
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="carton_nbr")
	public CartonHdr cartonHdr;
//	public CartonHdr getCartonHdr() {
//		return cartonHdr;
//	}
//	
//	public void setCartonHdr(CartonHdr hdr) {
//		cartonHdr=hdr;
//	}
	
	@OneToOne
	@JoinColumn(name="sku_id")
	public ItemMaster item;
	
	@Column(name="sku_id")
	public String sku_id;
    
//    public Long carton_seq_nbr;
    public String pkt_ctrl_nbr;
    public Long pkt_seq_nbr;
    public Long pack_code;
    public long to_be_pakd_units;
    public long units_pakd;
    public String line_item_stat;
    public String misc_instr_code_1;
    public String misc_instr_code_2;
    public String misc_instr_code_3;
    public String misc_instr_code_4;
    public String misc_instr_code_5;
    public Date create_date_time;
    public Date mod_date_time;
    public String batch_nbr;
    
    
    
    private static final long serialVersionUID = 11L;
    
    /**
     * Generic query helper for entity CartonDtl with id Long
     */
    public static Model.Finder<Long,CartonDtl> find = new Model.Finder<Long,CartonDtl>(Long.class, CartonDtl.class);

}