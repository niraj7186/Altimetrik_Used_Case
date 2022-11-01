package com.altimetrik.candidate.testapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.candidate.testapi.entity.userEntity;

/**
 * User Repository Interface to use all JPA Repository methods.
 */
@Repository
public interface userRepository extends JpaRepository<userEntity, Long> {


	Optional<userEntity> findByEmail(String email);

	void deleteByEmail(String email);

}
