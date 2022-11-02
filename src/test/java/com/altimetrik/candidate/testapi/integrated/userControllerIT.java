package com.altimetrik.candidate.testapi.integrated;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;
import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.accountrepository.accountRepository;
import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.repository.userRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;




/**
 * JUnit Test for Controller Integration Test
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class userControllerIT {


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private userRepository uRepo;

	@Autowired
	private accountRepository accRepo;

	@Autowired
	private ObjectMapper objMapper;

	@BeforeEach
	void setup()
	{
		uRepo.deleteAll();
		accRepo.deleteAll();
	}

	/**
	 * JUnit Test for creating a user REST API for Integration Test - Positive Scenario
	 */
	@Test
	public void createUser_IT() throws Exception {
		userEntity user = userEntity.builder()
				.name("Niraj Patel")
				.email("niraj@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(1500.00)
				.build();

		ResultActions savedUser = mockMvc.perform(MockMvcRequestBuilders.post("/save-users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(user)));

		savedUser.andExpect(MockMvcResultMatchers.status().isCreated())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(user.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(user.getEmail())));
	}

	/**
	 * JUnit Test for creating a user REST API for Integration Test - Negative Scenario
	 */
	@Test
	public void createUser_EmailExistReturnError_IT() throws Exception {

		userEntity alreadyUser = userEntity.builder()
				.name("Niraj Patel")
				.email("niraj@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(1500.00)
				.build();

		uRepo.save(alreadyUser);

		userEntity user = userEntity.builder()
				.name("Niraj Patel")
				.email("niraj@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(1500.00)
				.build();

		ResultActions savedUser = mockMvc.perform(MockMvcRequestBuilders.post("/save-users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(user)));

		savedUser.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * JUnit Test for get all user REST API for Integration Test
	 */
	@Test
	public void getAllUser_IT() throws Exception
	{
		List<userEntity> users = new ArrayList<>();
		users.add(userEntity.builder().name("Niraj Patel").email("niraj@gmail.com").monthlysal(6000.00).monthlyexp(1500.00).build());
		users.add(userEntity.builder().name("ManaliPatel").email("manali@gmail.com").monthlysal(5500.00).monthlyexp(1000.00).build());

		uRepo.saveAll(users);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/"));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.equalTo(2)));
	}

	/**
	 * JUnit Test for get user by EMAIL REST API for Integration Test - Positive Scenario
	 */
	@Test
	public void getUserByEmail_Found() throws Exception
	{
		List<userEntity> users = new ArrayList<>();
		users.add(userEntity.builder().name("Niraj Patel").email("niraj@gmail.com").monthlysal(6000.00).monthlyexp(1500.00).build());
		users.add(userEntity.builder().name("Manali Patel").email("manali@gmail.com").monthlysal(5500.00).monthlyexp(1000.00).build());

		uRepo.saveAll(users);

		ResultActions found = mockMvc.perform(MockMvcRequestBuilders.get("/email/{email}","manali@gmail.com"));

		found.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Manali Patel")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.equalTo("manali@gmail.com")));
	}

	/**
	 * JUnit Test for get user by EMAIL REST API for Integration Test - Negative Scenario
	 */
	@Test
	public void getUserByEmail_NotFound() throws Exception
	{
		List<userEntity> users = new ArrayList<>();
		users.add(userEntity.builder().name("Niraj Patel").email("niraj@gmail.com").monthlysal(6000.00).monthlyexp(1500.00).build());
		users.add(userEntity.builder().name("Manali Patel").email("manali@gmail.com").monthlysal(5500.00).monthlyexp(1000.00).build());

		uRepo.saveAll(users);

		ResultActions found = mockMvc.perform(MockMvcRequestBuilders.get("/email/{email}","manali12@gmail.com"));

		found.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * JUnit Test for delete a user by EMAIL REST API for Integration Test - Positive Scenario
	 */
	@Test
	public void deleteUserByEmail_Found() throws Exception
	{
		List<userEntity> users = new ArrayList<>();
		users.add(userEntity.builder().name("Niraj Patel").email("niraj@gmail.com").monthlysal(6000.00).monthlyexp(1500.00).build());
		users.add(userEntity.builder().name("Manali Patel").email("manali@gmail.com").monthlysal(5500.00).monthlyexp(1000.00).build());

		uRepo.saveAll(users);

		ResultActions found = mockMvc.perform(MockMvcRequestBuilders.delete("/delete/email?email=niraj@gmail.com"));

		found.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * JUnit Test for delete a user by EMAIL REST API for Integration Test - Negative Scenario
	 */
	@Test
	public void deleteUserByEmail_NotFound() throws Exception
	{
		List<userEntity> users = new ArrayList<>();
		users.add(userEntity.builder().name("Niraj Patel").email("niraj@gmail.com").monthlysal(6000.00).monthlyexp(1500.00).build());
		users.add(userEntity.builder().name("Manali Patel").email("manali@gmail.com").monthlysal(5500.00).monthlyexp(1000.00).build());

		uRepo.saveAll(users);

		ResultActions found = mockMvc.perform(MockMvcRequestBuilders.delete("/delete/email?email=niraj1@gmail.com"));

		found.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * JUnit test to save account details REST API for Integration Test.
	 */
	@Test
	public void saveAccountController() throws Exception
	{
		userEntity user = userEntity.builder()
				.name("Niraj Patel")
				.email("niraj@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(1500.00)
				.build();
		uRepo.save(user);
		accountEntity account = accountEntity.builder().build();

		accountModel accModel = new accountModel();
				accModel.setEmail("niraj@gmail.com");
				accModel.setPassword("123456");
				accModel.setBalance(4500.00);

				BeanUtils.copyProperties(accModel,account);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/create-account/{email}","niraj@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(account)));

		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(account.getEmail()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.balance",CoreMatchers.is(account.getBalance())));
	}

	/**
	 * JUnit test to get all account details REST API for Integration Test.
	 */
	@Test
	public void getAllAccountController() throws Exception
	{
		List<accountEntity> accounts = new ArrayList<>();
		accounts.add(accountEntity.builder().id(1L).email("niraj@gmail.com").password("123456").balance(2000.00).build());
		accounts.add(accountEntity.builder().id(2L).email("manali@gmail.com").password("123654").balance(2000.00).build());

		accRepo.saveAll(accounts);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/accounts"));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.equalTo(2)));
	}
}
