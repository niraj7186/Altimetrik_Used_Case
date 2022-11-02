package com.altimetrik.candidate.testapi.accountRepositoryTests;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;
import com.altimetrik.candidate.testapi.accountrepository.accountRepository;
import com.altimetrik.candidate.testapi.entity.userEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * JUnit test for Account Repository Layer using DataJPA.
 */
@DataJpaTest
public class accountRepositoryTest {

    @Autowired
    private accountRepository accRepo;

    private accountEntity accEntity;

    private userEntity user;

    @BeforeEach
    public void setup(){
        user = userEntity.builder()
                .name("Niraj Patel")
                .email("niraj@gmail.com")
                .monthlysal(6000.00)
                .monthlyexp(2000.00)
                .build();

        accEntity = accountEntity.builder()
                .email(user.getEmail())
                .password("123456")
                .balance(1500.00)
                .build();
    }

    /**
     * JUnit test to save account details in Account Repository Layer.
     */
    @Test
    public void saveAccount_basic(){

        accountEntity userAccount = accRepo.save(accEntity);


        assertThat(userAccount).isNotNull();
        assertThat(userAccount.getBalance()).isEqualTo(1500.00);
        assertThat(userAccount.getEmail()).isEqualTo("niraj@gmail.com");
    }

    /**
     * JUnit test to get all account details in Account Repository Layer.
     */
    @Test
    public void getAllAccounts_basic(){

        accEntity = accountEntity.builder()
                .email("niraj@gmail.com")
                .password("123456")
                .balance(1500.00)
                .build();

        accountEntity accEntity1 = accountEntity.builder()
                .email("manali@gmail.com")
                .password("123456")
                .balance(2000.00)
                .build();

        accRepo.save(accEntity);
        accRepo.save(accEntity1);

        List<accountEntity> accountList = accRepo.findAll();

        assertThat(accountList).isNotNull();
        assertThat(accountList.size()).isEqualTo(2);
        assertThat(accountList.get(1).getEmail()).isEqualTo("manali@gmail.com");
    }

}
