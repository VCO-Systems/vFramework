package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="carton_dtl")
public class CartonDtl extends Model {
    
	public String carton_nbr;
	
	private CartonHdr cartonHeader;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="carton_nbr")
	public CartonHdr getCartonHeader() {
		return cartonHeader;
	}
	
    
    
    public Long carton_seq_nbr;
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
    public String sku_id;
    
    
    private static final long serialVersionUID = 11L;
    
    /**
     * Generic query helper for entity CartonDtl with id Long
     */
    public static Model.Finder<Long,CartonDtl> find = new Model.Finder<Long,CartonDtl>(Long.class, CartonDtl.class);

}