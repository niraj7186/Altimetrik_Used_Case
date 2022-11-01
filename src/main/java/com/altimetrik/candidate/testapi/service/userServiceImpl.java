package com.altimetrik.candidate.testapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.repository.userRepository;

/**
 * User Service Implementation Class to define Business logic methods implemented in the Interface userService.
 */
@Service
public class userServiceImpl implements userService {

	
	private final userRepository uRepo;

	public userServiceImpl(userRepository uRepo) {
		this.uRepo=uRepo;
	}

	@Override
	public userEntity saveUser(userEntity user) throws userException, ConstraintViolationException {
		Optional<userEntity> email = uRepo.findByEmail(user.getEmail());
		if(email.isPresent())
		{
			throw new userException(userException.EmailExists(user.getEmail()));
		}		
		
		return uRepo.save(user);
	}

	@Override
	public userEntity getByEmail(String email) throws userException {
		Optional<userEntity> found = uRepo.findByEmail(email);
		if(!found.isPresent()) {
			throw new userException(userException.NotFoundException(email));
		}
			return found.get();
	}

	@Override
	public List<userEntity> getAllUsers() throws userException{
		List<userEntity> allusers = uRepo.findAll();
		if (allusers.isEmpty()) {
			throw new userException(userException.EmptyDB());
		}
		else {
			return new ArrayList<userEntity>(allusers);
		}
	}

	@Override
	@Transactional
	public void deleteUser(String email) throws userException {
		Optional<userEntity> found = uRepo.findByEmail(email);
		if(!found.isPresent())
		{
			throw new userException(userException.NotFoundException(email));
		}
		else
		{
			uRepo.deleteByEmail(email);
		}
		
		
	}

	
}

