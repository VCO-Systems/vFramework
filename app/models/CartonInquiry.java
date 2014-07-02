package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

//@Entity
//@Table(name="carton_hdr")
public class CartonInquiry extends Model{

    
    // carton_hdr fields
//    @Id
    public String carton_nbr;
    public String pkt_ctrl_nbr;
    public String whse;
    public String sku_id;
    
    // carton_dtl fields
    public Long carton_seq_nbr;
    public long units_pakd;
    
    // item_master fields
    public String season;
    public String season_yr;
    
    // Lists
    public List<CartonDtl> carton_dtls;
    public int nbrCartonDtls;
//    public List<OutbdLoad> outbd_loads;
    
    // One to ones
    public OutbdLoad outbd_load;
    public int nbrOutbdLoads;
    
    public List<ItemMaster> items;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Generic query helper for entity CartonHdr with id Long
     */
    public static Model.Finder<Long,CartonHdr> find = new Model.Finder<Long,CartonHdr>(Long.class, CartonHdr.class);

    
	
    
    
    
    

}