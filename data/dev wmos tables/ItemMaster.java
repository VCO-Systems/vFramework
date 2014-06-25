package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
//import play.db.jpa.*;
import play.db.ebean.*;

@Entity
@Table(name="item_master")
public class ItemMaster extends Model{

    
    //public Long id;
//    @Id
    public String carton_nbr;
    public Long carton_seq_nbr;
    public String pkt_ctrl_nbr;
    public Long pkt_seq_nbr;
    public Long pack_code;
    public Long to_be_pakd_units;
    public Long units_pakd;
    public String line_item_stat;
    public String misc_instr_code_1;
    public String misc_instr_code_2;
    public String misc_instr_code_3;
    public String misc_instr_code_4;
    public String misc_instr_code_5;
    public Date create_date_time;
    public Date mod_date_time;
    public Date user_id;
    public Date vendor_id;
    public String vendor_item_nbr;
    public Date cons_prty_date;
    public String invn_type;
    public String prod_stat;
    public String batch_nbr;
    public String sku_attr_1;
    public String sku_attr_2;
    
    
    private static final long serialVersionUID = 111L;
    
    /**
     * Generic query helper for entity ItemMaster with id Long
     */
    public static Model.Finder<Long,ItemMaster> find = new Model.Finder<Long,ItemMaster>(Long.class, ItemMaster.class);

}