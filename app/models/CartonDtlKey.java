package models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CartonDtlKey implements Serializable {

	private static final long serialVersionUID = 2847L;
	
	private String carton_nbr;
	private Long carton_seq_nbr;
	
	public CartonDtlKey() {
		
	}
	
	public CartonDtlKey(String carton_nbr, Long carton_seq_nbr) {
		carton_nbr=carton_nbr;
		carton_seq_nbr=carton_seq_nbr;
	}
	
	public String getCarton_nbr() {
		return carton_nbr;
	}

	public void setCarton_nbr(String carton_nbr) {
		this.carton_nbr = carton_nbr;
	}

	public Long getCarton_seq_nbr() {
		return carton_seq_nbr;
	}

	public void setCarton_seq_nbr(Long carton_seq_nbr) {
		this.carton_seq_nbr = carton_seq_nbr;
	}

	
	
	@Override
	public int hashCode() {
		return (int) carton_nbr.hashCode() + carton_nbr.length();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==this) return true;
		if (!(obj instanceof CartonDtlKey)) return false;
		if (obj == null) return false;
		CartonDtlKey pk = (CartonDtlKey) obj;
		return pk.carton_seq_nbr == carton_seq_nbr && pk.carton_nbr.equals(carton_nbr);
	}
}
