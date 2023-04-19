package app.core.filters;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.auth.JwtUtil;
import app.core.entities.Admin;
import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.entities.User;
import app.core.loginManager.ClientType;

public class AuthenticationFilter implements Filter {

	private JwtUtil jwtUtil;

	public AuthenticationFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("\n--- authentication filter started");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestMethod = httpRequest.getMethod();
		// to handle pre-flight requests in case of cross-origin situations
		if (requestMethod.equalsIgnoreCase("options")) {
			System.out.println("--- PREFLIGHT (authentication filter)");
			chain.doFilter(request, response);
		} else {
			try {
				// check for a valid token
				String authorization = httpRequest.getHeader("Authorization");
				StringTokenizer tokenizer = new StringTokenizer(authorization);
				String scheme = tokenizer.nextToken(); // Bearer
				String token = tokenizer.nextToken(); // JWT:
				
				System.out.println("---------- " + scheme);
				ClientType clientType = this.jwtUtil.extractClientType(token);
				switch(clientType) {
				case ADMIN: 
					Admin admin = this.jwtUtil.extractAdmin(token);
					httpRequest.setAttribute("ADMIN", admin);
					break;
					
				case COMPANY:
					Company company = this.jwtUtil.extractCompany(token);
					httpRequest.setAttribute("COMPANY", company);
					break;
					
				case CUSTOMER:
					Customer customer = this.jwtUtil.extractCustomer(token);
					httpRequest.setAttribute("CUSTOMER", customer);
					break;
					
				}
				chain.doFilter(httpRequest, response);

			} catch (Exception e) {
				// 2. block the request
				System.out.println("--- invalid token: " + e);
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500"); // for CORS

				// The HTTP WWW-Authenticate response header defines the HTTP authentication
				// methods ("challenges") that might be used to gain access to a specific
				// resource.
				resp.setHeader("WWW-Authenticate", "Bearer realm=\"General API\"");

				// The Access-Control-Expose-Headers response header allows a server to indicate
				// which
				// response headers should be made available to scripts running in the browser,
				// in
				// response to a cross-origin request.
				resp.setHeader("Access-Control-Expose-Headers", "*");

				resp.sendError(HttpStatus.UNAUTHORIZED.value(), "You need to login - " + e.getMessage());
			}
		}
	}

}
