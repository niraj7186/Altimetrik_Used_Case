package com.altimetrik.candidate.testapi.accountentity;

import lombok.Data;

/**
 * Account Model Class to map Account Entity Fields.
 */
@Data
public class accountModel {
	
	private String email;
	
	private String password;
	
	private Double balance;
}
