package com.momo.billpayment.service.exceptions;

/**
 * Exception Class for custom exceptions - doesn't stop the execution
 *
 */
public class BillPaymentException extends RuntimeException {
	/**
	 * Auto-generated SerialVersionID;
	 */
	private static final long serialVersionUID = 9026290603804836446L;
	private String message;

	public BillPaymentException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
