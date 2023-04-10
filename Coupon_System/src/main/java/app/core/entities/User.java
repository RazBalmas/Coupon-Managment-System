package app.core.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import app.core.loginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class  User {
	
	@Id
	private int id;
	private String email;
	private String password;
	private ClientType clientType;
	
}
