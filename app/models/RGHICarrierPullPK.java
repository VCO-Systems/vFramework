package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class RGHICarrierPullPK implements Serializable {
 
	private static final long serialVersionUID = 284728781L;
	
    private String whse;
    
    @Column(name="shipto_zip")
    private String shipToZip;

    @Column(name="ship_via")
    private String shipVia;
    
    public RGHICarrierPullPK() {
    }
    
    public RGHICarrierPullPK(String whse, String shipToZip, String shipVia) {
    	this.setWhse(whse);
    	this.setShipToZip(shipToZip);
    	this.setShipVia(shipVia);
    }
 
    
 
    public String getWhse() {
		return whse;
	}



	public void setWhse(String whse) {
		this.whse = whse;
	}



	public String getShipToZip() {
		return shipToZip;
	}



	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}



	public String getShipVia() {
		return shipVia;
	}



	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}



	public int hashCode() {
        return (int)this.shipToZip.hashCode()+ (int)this.shipVia.hashCode()+(int)this.whse.hashCode();
    }
 
    public boolean equals(Object obj) {
        if (!(obj instanceof RGHICarrierPullPK)) return false;
        RGHICarrierPullPK pk = (RGHICarrierPullPK) obj;
        return pk.whse.equals(this.whse) && pk.shipToZip.equals(this.shipToZip) && pk.shipVia.equals(this.shipVia);
    }
}