package com.altimetrik.candidate.testapi.accountservice;

import java.util.List;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;
import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.exception.userException;

/**
 * Account Service Interface to declare Business logic methods.
 */
public interface accountService {

	accountEntity createAccount(accountModel amodel, String email) throws userException;
	
	List<accountEntity> getAllAccounts() throws userException;
}
