package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Ship_Via")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipVia implements Serializable {

	private static final long serialVersionUID = 284729281L;
	
	@Id
	@Column(name="SHIP_VIA")
	private String shipVia;
	
	@Column(name="SHIP_VIA_DESC")
	private String shipViaDesc;
	
//	@Column(name="CARR_ID")
//	private String carrId;
//	
//	@Column(name="SERV_TYPE")
//	private String servType;
//	
//	@Column(name="LABEL_TYPE")
//	private String labelType;
//	
//	@Column(name="BILL_SHIP_VIA")
//	private String billShipVia;
//	
//	@Column(name="INSUR_COVER_CODE")
//	private String insurCoverCode;
//	
//	@Column(name="SERV_LEVEL_INDIC")
//	private String servLevelIndic;
//	
//	@Column(name="ICON")
//	private String icon;
//	
//	@Column(name="TMS_CARR_CODE")
//	private String tmsCarrCode;
//	
//	@Column(name="FRT_BASED_ON_NBR_OF_CARTON")
//	private String frtBasedOnNbrOfCarton;
//	
//	@Column(name="MODE_OF_TRANSPORT")
//	private Double modeOfTransport;
//	
//	@Column(name="INSUR_TYPE")
//	private String insurType;
	
	@Column(name="CREATE_DATE_TIME")
	@Temporal(TemporalType.DATE)
	private Date createDateTime;
	
	@Column(name="MOD_DATE_TIME")
	@Temporal(TemporalType.DATE)
	private Date modDateTime;
	
	@Column(name="USER_ID")
	private String userId;
	
	public String getShipVia() {
		return this.shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}
	
	public String getShipViaDesc() {
		return this.shipViaDesc;
	}

	public void setShipViaDesc(String shipViaDesc) {
		this.shipViaDesc = shipViaDesc;
	}
	
//	public String getCarrId() {
//		return this.carrId;
//	}
//
//	public void setCarrId(String carrId) {
//		this.carrId = carrId;
//	}
//	
//	public String getServType() {
//		return this.servType;
//	}
//
//	public void setServType(String servType) {
//		this.servType = servType;
//	}
//	
//	public String getLabelType() {
//		return this.labelType;
//	}
//
//	public void setLabelType(String labelType) {
//		this.labelType = labelType;
//	}
//	
//	public String getBillShipVia() {
//		return this.billShipVia;
//	}
//
//	public void setBillShipVia(String billShipVia) {
//		this.billShipVia = billShipVia;
//	}
//	
//	public String getInsurCoverCode() {
//		return this.insurCoverCode;
//	}
//
//	public void setInsurCoverCode(String insurCoverCode) {
//		this.insurCoverCode = insurCoverCode;
//	}
//	
//	public String getServLevelIndic() {
//		return this.servLevelIndic;
//	}
//
//	public void setServLevelIndic(String servLevelIndic) {
//		this.servLevelIndic = servLevelIndic;
//	}
//	
//	public String getIcon() {
//		return this.icon;
//	}
//
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}
//	
//	public String getTmsCarrCode() {
//		return this.tmsCarrCode;
//	}
//
//	public void setTmsCarrCode(String tmsCarrCode) {
//		this.tmsCarrCode = tmsCarrCode;
//	}
//	
//	public String getFrtBasedOnNbrOfCarton() {
//		return this.frtBasedOnNbrOfCarton;
//	}
//
//	public void setFrtBasedOnNbrOfCarton(String frtBasedOnNbrOfCarton) {
//		this.frtBasedOnNbrOfCarton = frtBasedOnNbrOfCarton;
//	}
//	
//	public Double getModeOfTransport() {
//		return this.modeOfTransport;
//	}
//
//	public void setModeOfTransport(Double modeOfTransport) {
//		this.modeOfTransport = modeOfTransport;
//	}
//	
//	public String getInsurType() {
//		return this.insurType;
//	}
//
//	public void setInsurType(String insurType) {
//		this.insurType = insurType;
//	}
//	
//	public Date getCreateDateTime() {
//		return this.createDateTime;
//	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	public Date getModDateTime() {
		return this.modDateTime;
	}

	public void setModDateTime(Date modDateTime) {
		this.modDateTime = modDateTime;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
