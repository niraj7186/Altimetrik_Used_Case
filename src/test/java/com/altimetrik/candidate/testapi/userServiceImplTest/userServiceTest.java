package com.altimetrik.candidate.testapi.userServiceImplTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.repository.userRepository;
import com.altimetrik.candidate.testapi.service.userServiceImpl;

/**
 * JUnit Test for User Service Layer.
 */
@ExtendWith(MockitoExtension.class)
public class userServiceTest {

	@Mock
	private userRepository uRepo;
	
	@InjectMocks
	private userServiceImpl uSrer;
	
	private userEntity user;
	@BeforeEach
	public void setup()
	{
		user = userEntity.builder()
				.id(1L)
				.name("Niraj Patel")
				.email("niraj@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(2000.00)
				.build();
	}

	/**
	 * JUnit Test to save user in User Service Layer.
	 */
	@Test
	public void saveUser_basic()
	{
		
		
		given(uRepo.findByEmail(user.getEmail())).willReturn(Optional.empty());
		given(uRepo.save(user)).willReturn(user);
		
		
		userEntity saveUser = null;
		try {
			saveUser = uSrer.saveUser(user);
		} catch (userException e) {
				System.out.println(e.getMessage());
		}
		
		assertThat(saveUser).isNotNull();
	}

	/**
	 * JUnit Test to test exception in save user method in User Service Layer.
	 */
	@Test
	public void saveUserToTestException_basic()
	{
		given(uRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));
		
		org.junit.jupiter.api.Assertions.assertThrows(userException.class, () -> {uSrer.saveUser(user);});
		
		verify(uRepo, never()).save(user);
	}

	/**
	 * JUnit Test to get all users in User Service Layer.
	 */
	@Test
	public void getAllUser_basic()
	{
		userEntity user1 = userEntity.builder()
				.id(2L)
				.name("Manali Patel")
				.email("manali@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(2000.00)
				.build();


		given(uRepo.findAll()).willReturn(Arrays.asList(user, user1));
		
		List<userEntity> getAllUser = null;
		try {
			getAllUser = uSrer.getAllUsers();
		} catch (userException e) {
				System.out.println(e.getMessage());
		}
		
		assertThat(getAllUser).isNotNull();
		assertThat(getAllUser.get(1).getEmail()).isEqualTo("manali@gmail.com");
	}

	/**
	 * JUnit Test to test exception in get all user in User Service Layer.
	 */
	@Test
	public void getAllUserToTestException_basic()
	{
		given(uRepo.findAll()).willReturn(Collections.emptyList());
		
		org.junit.jupiter.api.Assertions.assertThrows(userException.class, uSrer::getAllUsers);
		
	}

	/**
	 * JUnit Test to get user by EMAIL in User Service Layer.
	 */
	@Test
	public void getUserByEmail_basic()
	{
		given(uRepo.findByEmail("niraj@gmail.com")).willReturn(Optional.of(user));
		
		userEntity found = null;
		try {
			found = uSrer.getByEmail(user.getEmail());
		} catch (userException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		assertThat(found).isNotNull();
	}

	/**
	 * JUnit Test to test exception in get user by EMAIL in User Service Layer.
	 */
	@Test
	public void getUserByEmailToTestException_basic()
	{
		given(uRepo.findByEmail(user.getEmail())).willReturn(Optional.empty());
		
		org.junit.jupiter.api.Assertions.assertThrows(userException.class, () -> {uSrer.getByEmail(user.getEmail());});
		
	}

	/**
	 * JUnit Test to delete user by EMAIL in User Service Layer.
	 */
	@Test
	public void deleteUserByEmail_basic()
	{
		given(uRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));
		BDDMockito.willDoNothing().given(uRepo).deleteByEmail(user.getEmail());
		
		try {
			uSrer.deleteUser(user.getEmail());
		} catch (userException e) {
			System.out.println(e.getMessage());
		}
		
		verify(uRepo,times(1)).deleteByEmail(user.getEmail());
	}
}
