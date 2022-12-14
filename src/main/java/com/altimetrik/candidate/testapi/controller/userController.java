package com.altimetrik.candidate.testapi.controller;

import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.accountservice.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.service.userService;

/**
 * REST Controller for User and Account creation.
 */
@RestController
public class userController {

	@Autowired
	private userService uService;

	@Autowired
	private accountService accService;


	/**
	 * REST API to save user.
	 */
	@PostMapping(value = "/save-users")
	public ResponseEntity<?> saveUsers(@RequestBody userEntity user)
	{
		try {
			uService.saveUser(user);
			return new ResponseEntity<userEntity>(user,HttpStatus.CREATED);
		} catch (userException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * REST API to get user by EMAIL.
	 */
	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email)
	{
		try {
			return new ResponseEntity<>(uService.getByEmail(email),HttpStatus.OK);
		} catch (userException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * REST API to get all user.
	 */
	@GetMapping("/")
	public ResponseEntity<?> getAllUsers(){
		try {
			return new ResponseEntity<>(uService.getAllUsers(),HttpStatus.OK);
		} catch (userException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * REST API to delete user by EMAIL.
	 */
	@DeleteMapping("/delete/email")
	public ResponseEntity<?> deleteUserByEmail(@RequestParam String email)
	{
		try {
			uService.deleteUser(email);
			return new ResponseEntity<>("User with "+email+" successfully deleted!",HttpStatus.OK);
		} catch (userException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}





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


