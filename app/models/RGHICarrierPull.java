package models;

import java.io.Serializable;

import javax.persistence.*;

import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the RGHICarrierPull database table.
 * VC: Auto-generated by JPA table-to-entity generator July 30.
 * 
 */
@Entity
@IdClass(RGHICarrierPullPK.class)
@Table(name="RGHI_Carrier_Pull")
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedQuery(name="RGHICarrierPull.findAll", query="SELECT p FROM RGHICarrierPull p")
public class RGHICarrierPull implements Serializable {
	
	private static final long serialVersionUID = 284729281L;
	
	@Id
	@Column(name="WHSE")
	private String whse;
	
	@Id
	@Column(name="SHIPTO_ZIP")
	private String shipToZip;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@Id
	@JoinColumn(name="SHIP_VIA", referencedColumnName="SHIP_VIA")
	@JsonUnwrapped
	private ShipVia shipVia;
	
	@Column(name="PULL_TRLR_CODE")
	private String pullTrlrCode;
	
	@Column(name="PULL_TIME")
	private String pullTime;
	
	@Column(name="PULL_TIME_AMPM")
	private String pullTimeAMPM;
	
	@Column(name="ANYTEXT1")
	private String anyText1;

	@Column(name="ANYNBR1")
	private String anyNbr1;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE_TIME")
	private Date createDateTime;
	
	@Temporal(TemporalType.DATE)
	@Column(name="MOD_DATE_TIME")
	private Date modDateTime;
	
	@Column(name="USER_ID")
	private String userId;

	public String getWhse() {
		return whse;
	}

	public void setWhse(String whse) {
		this.whse = whse;
	}

	public String getShipToZip() {
		return this.shipToZip;
	}

	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}

	public ShipVia getShipVia() {
		return this.shipVia;
	}

	public void setShipVia(ShipVia shipVia) {
		this.shipVia = shipVia;
	}

	public String getPullTrlrCode() {
		return pullTrlrCode;
	}

	public void setPullTrlrCode(String pullTrlrCode) {
		this.pullTrlrCode = pullTrlrCode;
	}

	public String getPullTime() {
//		Time return_date = new Time();
//		try {
//			StringBuilder vals = new StringBuilder();
//			vals.append(pullTime.toString());
//			SimpleDateFormat sm = new SimpleDateFormat("hh:mm a");
//			String strDate = sm.format(pullTime);
//			vals.append(" | " + strDate.toString());
//			return_date = sm.parse(strDate);
//			vals.append(" | " + strDate);
//			System.out.println(vals);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//SimpleDateFormat df = new SimpleDateFormat("h:m a");
//		Date dt = df.format(this.pullTime);		
		return this.pullTime;
	}

	public void setPullTime(String pullTime) {
		this.pullTime = pullTime;
	}
	
	public String getPullTimeAMPM() {
		return pullTimeAMPM;
	}

	public void setPullTimeAMPM(String pullTimeAMPM) {
		this.pullTimeAMPM = pullTimeAMPM;
	}

	public String getAnyText1() {
		return anyText1;
	}

	public void setAnyText1(String anyText1) {
		this.anyText1 = anyText1;
	}

	public String getAnyNbr1() {
		return anyNbr1;
	}

	public void setAnyNbr1(String anyNbr1) {
		this.anyNbr1 = anyNbr1;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getModDateTime() {
		return modDateTime;
	}

	public void setModDateTime(Date modDateTime) {
		this.modDateTime = modDateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}