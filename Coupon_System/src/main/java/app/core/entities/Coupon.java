package app.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity
public class Coupon {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	private int amount;

	private double price;

	private LocalDate startDate;

	private LocalDate endDate;

	private String title;

	private String description;

	private String image;
	
	@Enumerated(EnumType.STRING)
	private Catagory catagory;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "User_Coupons",
	    joinColumns = @JoinColumn(name = "coupon_id"),
	    inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> userList;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "Company_id", nullable = false)
	private Company company;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable (
			name = "Customer_Coupons", 
			joinColumns = @JoinColumn(name = "coupon_id"),
			inverseJoinColumns = @JoinColumn(name = "customer_id")
			)
	private List<Customer> customers;
	
	
	
	
	public void setCustomers(Customer customer) {
		if ( customer == null) {
			return;
		}
		if (this.customers == null) {
			this.customers = new ArrayList<>();
			customers.add(customer);
			this.amount -= 1;
		}
		else {
			this.customers.add(customer);
		}
	}

	public void deleteCustomer(int customerID) {
		Iterator<Customer> iterator = customers.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId() == customerID) {
				iterator.remove();
				this.amount += 1;
			}
		}
	}

	@Override
	public String toString() {
		return "Coupon [id =" + id + ", Company_ID =" +company.getId()+", amount =" + amount + ", price =" + price + ", startDate =" + startDate
				+ ", endDate =" + endDate + ", title =" + title + ", description =" + description + ", image =" + image
				+ ", catagory =" + catagory + "]";
	}
	
}
