package app.core.auth;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import app.core.loginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private ClientType clientType;
	

}
