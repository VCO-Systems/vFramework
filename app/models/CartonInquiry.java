package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;
// import play.db.ebean.*;

//@Entity
//@Table(name="carton_hdr")
public class CartonInquiry {
	
	public CartonInquiry() {
		
	}
	
	public CartonInquiry(String carton_nbr_in, String whse_in) {
		carton_nbr=carton_nbr_in;
		whse=whse_in;
	}

	// CARTON_HDR fields
    public String carton_nbr;
    public String pkt_ctrl_nbr;
    public String whse;
    public String wave_nbr;
    public Long carton_creation_code;
    public String shpmt_nbr;
    
    // CARTON_DTL fields
    public Long carton_seq_nbr;
    public long units_pakd;
    public long to_be_pakd_units;
    
    // ITEM_MASTER fields
    public String season;
    public String season_yr;
    public String style;
    public String style_sfx;
    public String color;
    public String color_sfx;
    public String sku_brcd;
    public String sku_desc;
    public String size_desc;
    public String sec_dim;
    public String qual;
    
    private static final long serialVersionUID = 14L;
    

}