package models;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.*;
import play.db.jpa.*;
//import play.db.ebean.*;

@Entity
@Table(name="carton_dtl")
@IdClass(CartonDtlKey.class)
public class CartonDtl {
    
	private static final long serialVersionUID = 11L;
	
	@Id
	@AttributeOverrides({
		@AttributeOverride(name = "cartonNbr",
		column = @Column(name="carton_nbr", updatable=false,insertable=false)),
		@AttributeOverride(name = "cartonSeqNbr",
		column = @Column(name="carton_seq_nbr", updatable=false,insertable=false))
	})
	
	private String carton_nbr;
	private Long carton_seq_nbr;
	
	public String getCartonNbr() {
		return carton_nbr;
	}
	
	public void setCartonNbr(String c) {
		carton_nbr=c;
	}
	
	public Long getCartonSeqNbr() {
		return carton_seq_nbr;
	}
	
	public void setCartonSeqNbr(Long c) {
		carton_seq_nbr = c;
	}
//	public String carton_nbr;
	
	
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="carton_nbr",insertable=false,updatable=false)
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
	public ItemMaster itemMaster;
	
//	@Column(name="sku_id")
//	public String sku_id;
    
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
    
    
    
    
    

}