package app.core.exceptions;


import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;

/**
 * @author razbalmas Extends {@link Exception}, exceptions of data layer of the
 *         coupon system :
 * 
 * @Beans - {@link Company} {@link Coupon} {@link Customer}
 * 
 * @DAO - {@link CompaniesDAO} {@link CouponDAO} {@link CustomerDAO}
 *
 * @ConnectionPool - {@link CouponSystemException}
 * 
 */
@Component
public class CouponSystemException extends Exception {

	/**
	 * Version of exception.
	 */
	private static final long serialVersionUID = 1L;

	public CouponSystemException() {
	}

	public CouponSystemException(String message) {
		super(message);
	}

	public CouponSystemException(Throwable cause) {
		super(cause);
	}

	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
