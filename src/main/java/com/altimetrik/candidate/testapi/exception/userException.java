package com.altimetrik.candidate.testapi.exception;


/**
 * Custom Exception class that throws graceful exception messages.
 */
public class userException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public userException(String message) {
		super(message);
	}
	
	public static String EmailExists(String email)
	{
		return email+" already exists for a user.";
	}
	
	public static String NotFoundException(String email)
	{
		return "User with "+email+" not found";
	}

	public static String EmailMismatch(String email)
	{
		return "User email "+email+" doesnot match";
	}

	public static String EmptyDB()
	{
		return "DataBase empty.";
	}
	
	public static String BalanceLow(Double bal)
	{
		return "Account can not be created as your balance $"+bal+" is less than $1000.00";
	}
	
}
