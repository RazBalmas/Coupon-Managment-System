package app.core.auth;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import app.core.entities.Admin;
import app.core.entities.Company;
import app.core.entities.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public abstract class JwtUtilAbstract<T, ID> {

	private String alg = SignatureAlgorithm.HS256.getJcaName();
//	@Value("${jwt.util.secret}")
	private final String secret = "aaaaaaaaa1aaaaaaaaa2aaaaaaaaa3aaaaaaaaa4aaa";

	private Key key;

//	@Value("${jwt.util.chrono.unit}")
	private final String chronoUnit = ChronoUnit.MINUTES.name(); // time units

//	@Value("${jwt.util.chrono.unit.number}")
	private final long unitNumber = 30; // number of time units

	private JwtParser jwtParser;

	@PostConstruct
	void init() {
		this.key = new SecretKeySpec(Base64.getDecoder().decode(secret), alg);
		jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
	}

	

	protected String createToken(Map<String, Object> claims, ID id) {

		Instant now = Instant.now();
		Instant exp = now.plus(unitNumber, ChronoUnit.valueOf(chronoUnit));
		String token = Jwts.builder()

				.setClaims(claims)

				.setSubject(id.toString())

				.setIssuedAt(Date.from(now))

				.setExpiration(Date.from(exp))

				// sign with a Key object and a signature algorithm
				.signWith(key)

				.compact();
		
		
		return token;
	}

	public abstract T extractUser(String token) throws JwtException;
	public abstract T extractAdmin(String token) throws JwtException;
	public abstract T extractCompany(String token) throws JwtException;
	public abstract T extractCustomer(String token) throws JwtException;

	protected Claims extractAllClaims(String token) throws JwtException {
		Jws<Claims> jwt = this.jwtParser.parseClaimsJws(token);
		return jwt.getBody();
	}

	public abstract String generateCompanyToken(Company company);

	public abstract String generateCustomerToken(Customer customer);

	public abstract String generateAdminToken(Admin admin);

}
