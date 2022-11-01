package com.altimetrik.candidate.testapi.repository;

import com.altimetrik.candidate.testapi.entity.userEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JUnit Test for User Repository Layer - Integration Testing.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class userRepositoryIT {

    @Autowired
    private userRepository uRepo;

    private userEntity user;

    @BeforeEach
    public void setup(){
        user = userEntity.builder()
                .name("Niraj Patel")
                .email("niraj@gmail.com")
                .monthlysal(6000.00)
                .monthlyexp(2000.00)
                .build();

        uRepo.deleteAll();

    }

    /**
     * JUnit Integration test to save user in User Repository Layer.
    */
    @Test
    public void saveUser_basic(){

        userEntity savedUser = uRepo.save(user);

        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("niraj@gmail.com");
    }

    /**
     * JUnit Integration test to get all user in User Repository Layer.
     */
    @Test
    public void getAllUsers_basic(){

        userEntity user1 = userEntity.builder()
                .name("Manali Patel")
                .email("manali@gmail.com")
                .monthlysal(6000.00)
                .monthlyexp(2000.00)
                .build();

        uRepo.save(user);
        uRepo.save(user1);

        List<userEntity> userList = uRepo.findAll();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
        assertThat(userList.get(1).getEmail()).isEqualTo("manali@gmail.com");
    }

    /**
     * JUnit Integration test to get user by EMAIL in User Repository Layer.
     */
    @Test
    public void getUserByEmail_basic(){
        uRepo.save(user);

        userEntity found = uRepo.findByEmail(user.getEmail()).get();

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualToIgnoringCase("Niraj Patel");
    }

    /**
     * JUnit Integration test to delete user by EMAIL in User Repository Layer.
     */
    @Test
    public void deleteByEmail(){
        uRepo.save(user);

        uRepo.deleteByEmail(user.getEmail());
        Optional<userEntity> found = uRepo.findByEmail(user.getEmail());

        assertThat(found).isEmpty();
    }
}
