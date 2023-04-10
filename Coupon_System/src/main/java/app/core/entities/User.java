package app.core.entities;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import app.core.loginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class  User {
	
	@Id
	private int id;
	private String email;
	private String password;
	private ClientType clientType;
	
}
