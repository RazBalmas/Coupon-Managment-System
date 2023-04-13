package app.core.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import org.springframework.lang.Nullable;

import app.core.loginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
	
	@Id
	private int id;
	private String email;
	private String password;
	
	@Nullable
	private String name;
	@Nullable
	private String firstName;
	@Nullable
	private String lastName;	
	@Nullable
	@ManyToMany(mappedBy = "userList")
	private List<Coupon> couponList;
	@Enumerated(EnumType.STRING)
	private ClientType clientType;
	
}
