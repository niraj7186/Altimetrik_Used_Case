package com.altimetrik.candidate.testapi.accountrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;

/**
 * Account Repository Interface to use all JPA Repository methods.
 */
public interface accountRepository extends JpaRepository<accountEntity, Long> {

}
