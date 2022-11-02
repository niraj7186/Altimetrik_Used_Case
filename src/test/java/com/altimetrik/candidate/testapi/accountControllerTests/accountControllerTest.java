package com.altimetrik.candidate.testapi.accountControllerTests;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;
import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.accountservice.accountService;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.service.userService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

/**
 * JUnit test for Account Controller (HTTP REST API) Layer.
 */
@WebMvcTest
public class accountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private accountService accSer;

	@MockBean
	private userService uService;

	@Autowired
	private ObjectMapper objMapper;
	
	//private accountEntity accEntity;

	private accountEntity account = accountEntity.builder().build();

	private accountModel accModel;
	@BeforeEach
	public void setup()
	{
		accModel = new accountModel();
		accModel.setEmail("niraj@gmail.com");
		accModel.setPassword("123456");
		accModel.setBalance(2000.00);
	}

	/**
	 * JUnit test to save account details REST API in Account Controller (HTTP REST API) Layer.
	 */
	@Test
	public void saveAccountController() throws Exception
	{
		BeanUtils.copyProperties(accModel,account);
		try {
			given(accSer.createAccount(accModel, "niraj@gmail.com")).willReturn(account);

		} catch (userException e) {
			System.out.println(e.getMessage());
		}

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/create-account/{email}","niraj@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(account)));

		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(account.getEmail()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.balance",CoreMatchers.is(account.getBalance())));
	}

	/**
	 * JUnit test to get all account details REST API in Account Controller (HTTP REST API) Layer.
	 */
	@Test
	public void getAllAccountController() throws Exception
	{
		List<accountEntity> accounts = new ArrayList<>();
		accounts.add(accountEntity.builder().id(1L).email("niraj@gmail.com").password("123456").balance(2000.00).build());
		accounts.add(accountEntity.builder().id(2L).email("manali@gmail.com").password("123654").balance(2000.00).build());
		
		try {
			given(accSer.getAllAccounts()).willReturn(accounts);
		} catch (userException e) {
			System.out.println(e.getMessage());
		}
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/accounts"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.equalTo(2)));
	}

}
