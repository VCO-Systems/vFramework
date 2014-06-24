package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="carton_dtl")
public class CartonDtl extends Model {

    
    //public Long id;
    @Id
    public String carton_nbr;
    public Long carton_seq_nbr;
    public String pkt_ctrl_nbr;
    public Long pkt_seq_nbr;
    public Long pack_code;
    public long to_be_pakd_units;
    
    
    
    
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
    public static Model.Finder<Long,CartonDtl> find = new Model.Finder<Long,CartonDtl>(Long.class, CartonDtl.class);

}