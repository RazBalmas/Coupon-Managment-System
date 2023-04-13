package app.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import app.core.loginManager.ClientType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Admin extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final int id = 1;
	
    @Column(unique = true)
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
