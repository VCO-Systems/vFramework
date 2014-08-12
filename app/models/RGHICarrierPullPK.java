package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RGHICarrierPullPK implements Serializable {
 
	
    private String whse;
    
    @Column(name="shipto_zip")
    private String shiptoZip;

    @Column(name="ship_via")
    private String shipVia;
    
    public RGHICarrierPullPK() {
    }
 
    
 
    public String getWhse() {
		return whse;
	}



	public void setWhse(String whse) {
		this.whse = whse;
	}



	public String getShipToZip() {
		return shiptoZip;
	}



	public void setShipToZip(String shipToZip) {
		this.shiptoZip = shipToZip;
	}



	public String getShipVia() {
		return shipVia;
	}



	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}



	public int hashCode() {
        return (int)this.shiptoZip.hashCode()+ (int)this.shipVia.hashCode()+(int)this.whse.hashCode();
    }
 
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof RGHICarrierPullPK)) return false;
        RGHICarrierPullPK pk = (RGHICarrierPullPK) obj;
        return pk.whse.equals(this.whse) && pk.shiptoZip.equals(this.shiptoZip) && pk.shipVia.equals(this.shipVia);
    }
}