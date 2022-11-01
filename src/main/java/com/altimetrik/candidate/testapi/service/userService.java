package com.altimetrik.candidate.testapi.service;

import java.util.List;

import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;

/**
 * User Service Interface to declare Business logic methods.
 */
public interface userService {

	public userEntity saveUser(userEntity user) throws userException;
	
	public userEntity getByEmail(String email) throws userException;
	
	public List<userEntity> getAllUsers() throws userException;
	
	public void deleteUser(String email) throws userException;
}
