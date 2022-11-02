package com.altimetrik.candidate.testapi.accountrepository;

import com.altimetrik.candidate.testapi.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;

import java.util.Optional;

/**
 * Account Repository Interface to use all JPA Repository methods.
 */
public interface accountRepository extends JpaRepository<accountEntity, Long> {

    Optional<accountEntity> findByEmail(String email);
}
