package models;

import java.util.*;

import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

/**
 * Company entity managed by JPA
 */
@Entity 
public class CartonHdr {

    @Id
    public Long id;
    
    private String carton_nbr;
    public String pkt_ctrl_nbr;
    
    public static CartonHdr findById(Long id) {
        return JPA.em().find(CartonHdr.class, id);
    }

    public static Map<String,String> options() {
        @SuppressWarnings("unchecked")
				List<CartonHdr> cartonHdrs = JPA.em().createQuery("from CartonHdr order by carton_nbr").getResultList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(CartonHdr c: cartonHdrs) {
            options.put(c.id.toString(), c.carton_nbr);
        }
        return options;
    }

}