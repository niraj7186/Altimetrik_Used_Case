package com.altimetrik.candidate.testapi.controllerTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.ArrayList;
import java.util.List;

import com.altimetrik.candidate.testapi.accountservice.accountService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.service.userService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JUnit Test for User Controller (HTTP REST API) Layer.
 */
@WebMvcTest
public class userControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private userService uService;

	@MockBean
	private accountService accService;

	@Autowired
	private ObjectMapper objMapper;
	
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
	 * JUnit Test to save user REST API in User Controller (HTTP REST API) Layer.
	 */
	@Test
	public void saveUserController() throws Exception
	{
		
		try {
			given(uService.saveUser(user)).willReturn(user);
			
		} catch (userException e) {
			System.out.println(e.getMessage());
		}
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/save-users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(user)));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(user.getEmail())));
	}

	/**
	 * JUnit Test to get all user REST API in User Controller (HTTP REST API) Layer.
	 */
	@Test
	public void getAllUsersController() throws Exception
	{
		List<userEntity> users = new ArrayList<>();
		users.add(user);
		users.add(userEntity.builder().id(2L).name("Manali Patel").email("manali@gmail.com").monthlysal(6000.00).monthlyexp(2000.00).build());
		
		try {
			given(uService.getAllUsers()).willReturn(users);
		} catch (userException e) {
			System.out.println(e.getMessage());
		}
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.equalTo(2)));
	}

	/**
	 * JUnit Test to get all user by EMAIL REST API in User Controller (HTTP REST API) Layer - Positive Scenario
	 */
	@Test
	public void getUserByEmail_basic() throws Exception {
		String email = "niraj@gmail.com";
		try {
			given(uService.getByEmail(user.getEmail())).willReturn(user);
		} catch (userException e) {
			System.out.println(e.getMessage());
		}

		ResultActions found = mockMvc.perform(MockMvcRequestBuilders.get("/email/{email}", email));

		found.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(email)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(user.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.monthlysal", CoreMatchers.is(user.getMonthlysal())));
	}

	/**
	 * JUnit Test to get user by EMAIL REST API in User Controller (HTTP REST API) Layer - Negative scenario
	 */
	@Test
	public void getUserByEmail_withNotFound() throws Exception {
		String email = "niraj@gmail.com";
		try {
			given(uService.getByEmail(user.getEmail())).willThrow(userException.class);
		} catch (userException e) {
			System.out.println(e.getMessage());
		}

		ResultActions found = mockMvc.perform(MockMvcRequestBuilders.get("/email/{email}", email));

		found.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andDo(MockMvcResultHandlers.print());

	}

	/**
	 * JUnit Test to delete user by EMAIL REST API in User Controller (HTTP REST API) Layer.
	 */
	@Test
	public void deleteUserByEmail_basic() throws Exception {

		String email = "niraj@gmail.com";
		try {
			willDoNothing().given(uService).deleteUser(email);
		} catch (userException e) {
			System.out.println(e.getMessage());
		}

		ResultActions deleted = mockMvc.perform(MockMvcRequestBuilders.delete("/delete/email?email=niraj@gmail.com",email));

		deleted.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
}
