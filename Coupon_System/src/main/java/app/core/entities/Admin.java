package app.core.entities;

import javax.persistence.Entity;

import app.core.loginManager.ClientType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Admin extends User {

	private final int id = 1;
	private final String email = "admin@admin.com"; 
	private final String password = "admin";
	private final ClientType clientType = ClientType.ADMIN;

	private static class getInstance {
		
		private static final Admin instance = new Admin();
			}
	
	public static Admin getAdmin() {
		return getInstance.instance;
	}

}
