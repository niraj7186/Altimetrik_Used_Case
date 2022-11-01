package com.altimetrik.candidate.testapi.accountcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.accountservice.accountService;
import com.altimetrik.candidate.testapi.exception.userException;

/**
 * REST Controller for Account creation.
 */
@RestController
public class accountController {

	@Autowired
	private accountService accService;

	/**
	 * REST API to create account of the given user (Email id of the user required).
	 */
	@PostMapping("/create-account/{email}")
	public ResponseEntity<?> createAccount(@RequestBody accountModel acc, @PathVariable("email") String email)
	{
		try {
			accService.createAccount(acc, email);
			return new ResponseEntity<accountModel>(acc,HttpStatus.CREATED);
		} catch (userException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * REST API to get all accounts created.
	 */
	@GetMapping("/accounts")
	public ResponseEntity<?> getAllAccounts()
	{
		try {
			return new ResponseEntity<>(accService.getAllAccounts(),HttpStatus.OK);
		} catch (userException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
