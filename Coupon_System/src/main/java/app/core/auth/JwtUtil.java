package app.core.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import app.core.entities.User;
import app.core.loginManager.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtil extends JwtUtilAbstract<User, Integer> {

	@Override
	public String generateToken(User user) {
		// create a map of all needed claims
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", user.getEmail());
		claims.put("clientType", user.getClientType());
		String token = this.createToken(claims, user.getId());
		return token;
	}

	@Override
	public User extractUser(String token) throws JwtException {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String email = claims.get("email", String.class);
		String firstName = claims.get("firstName", String.class);
		String lastName = claims.get("lastName", String.class);
		String userName = claims.get("username", String.class);
		ClientType clientType = ClientType.valueOf(claims.get("role", String.class));
		User user = new User(id, email, null, clientType);
		return user;
	}



}
