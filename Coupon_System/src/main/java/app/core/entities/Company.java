package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString (exclude = "coupons") 
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity
public class Company extends User{

	public Company (int id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>(null);
	}
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String name;
	
	@Column(unique = true)
	private String email;

	@Column(name ="password")
	private String password;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	@Nullable
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
