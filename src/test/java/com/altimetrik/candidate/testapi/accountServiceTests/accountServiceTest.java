package com.altimetrik.candidate.testapi.accountServiceTests;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;
import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.accountrepository.accountRepository;
import com.altimetrik.candidate.testapi.accountservice.accountServiceImpl;
import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.repository.userRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * JUnit Test in Account Service Layer using MOCKITO and ASSERTJ.
 */
@ExtendWith(MockitoExtension.class)
public class accountServiceTest {

	@Mock
	private userRepository uRepo;
	@Mock
	private accountRepository accRepo;
	
	@InjectMocks
	private accountServiceImpl accService;
	
	private userEntity user;
	@BeforeEach
	public void setup()
	{
		user = userEntity.builder()
				.id(1L)
				.name("Niraj Patel")
				.email("niraj@gmail.com")
				.monthlysal(6000.00)
				.monthlyexp(3000.00)
				.build();
	}

	/**
	 * JUnit Test to save account details in Account Service Layer.
	 */
	@Test
	public void saveAccount_basic()
	{
		accountEntity acc = accountEntity.builder().build();
		accountModel accModel = new accountModel();
		accModel.setEmail(user.getEmail());
		accModel.setPassword("123456");
		accModel.setBalance(user.getMonthlysal()-user.getMonthlyexp());

		given(uRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));
		BeanUtils.copyProperties(accModel,acc);
		acc.setId(1L);
		given(accRepo.save(acc)).willReturn(acc);

		accountEntity saveUser = null;
		try {
			saveUser = accService.createAccount(accModel,user.getEmail());
		} catch (userException e) {
			System.out.println(e.getMessage());
		}

		assertThat(saveUser).isNotNull();
	}

	/**
	 * JUnit Test to get all accounts in Account Service Layer.
	 */
	@Test
	public void getAllUser_basic()
	{

		accountEntity acc = accountEntity.builder().build();
		accountEntity acc1 = accountEntity.builder().build();

		accountModel accModel = new accountModel();
		accModel.setEmail("niraj@gmail.com");
		accModel.setPassword("123456");
		accModel.setBalance(2500.00);

		accountModel accModel1 = new accountModel();
		accModel.setEmail("manali@gmail.com");
		accModel.setPassword("123456");
		accModel.setBalance(2000.00);

		BeanUtils.copyProperties(accModel,acc);
		BeanUtils.copyProperties(accModel1,acc1);

		given(accRepo.findAll()).willReturn(Arrays.asList(acc,acc1));

		List<accountEntity> getAccounts = null;
		try {
			getAccounts = accService.getAllAccounts();
		} catch (userException e) {
			System.out.println(e.getMessage());
		}

		assertThat(getAccounts).isNotNull();
		assertThat(getAccounts.size()).isEqualTo(2);
	}

}
