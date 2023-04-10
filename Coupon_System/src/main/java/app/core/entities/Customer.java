package app.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "owned_coupons")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Customer  extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firstName;

	private String lastName;
	@Column(unique = true)
	private String email;

	@Column(name ="password")
	private String password;

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "Customer_Coupons", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private List<Coupon> owned_coupons;

	public void addCoupon(Coupon coupon) {
		if (owned_coupons == null) {
			this.owned_coupons = new ArrayList<>();
		}
		owned_coupons.add(coupon);
	}

	public void deleteCoupon(int couponID) {
		Iterator<Coupon> iterator = owned_coupons.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId() == couponID) {
				iterator.remove();
			}
		}
	}
}
