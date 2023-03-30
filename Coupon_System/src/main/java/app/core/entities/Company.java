package app.core.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString (exclude = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity
public class Company {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String name;
	
	@Column(unique = true)
	private String email;

	private String Password;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private List<Coupon> coupons;

	public void setCoupons(List<Coupon> coupons) {
		if ( coupons == null) {
			return;
		}
		
		coupons.forEach(c -> c.setCompany(this));
		if (this.coupons == null) {
			this.coupons = coupons;
		}
		else {
			this.coupons.addAll(coupons);
		}
	}
	
	
}
