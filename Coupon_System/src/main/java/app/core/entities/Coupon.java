package app.core.entities;

import java.sql.Date;
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

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate startDate;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate endDate;

	private String title;

	private String description;

	private String image;
	
	@Enumerated(EnumType.STRING)
	private Catagory catagory;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "Customer_Coupons",
	    joinColumns = @JoinColumn(name = "coupon_id"),
	    inverseJoinColumns = @JoinColumn(name = "Customer_id"))
	private List<Customer> customerList;
	
	@JsonIgnore
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
	
	public void setStartDate(String date) {
		LocalDate localDate = Date.valueOf(date).toLocalDate();
		this.startDate = localDate;
	}
	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}
	public void setEndtDate(String date) {
		LocalDate localDate = Date.valueOf(date).toLocalDate();
		this.endDate = localDate;
	}
	public void setEndtDate(LocalDate date) {
		this.endDate = date;
	}
}
