package com.altimetrik.candidate.testapi.accountentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;


/**
 * Account Entity Class with DB Table and DB Fields.
 */
@Data
@Entity
@Builder
@Table(name = "user_acc")
public class accountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Double balance;

	public accountEntity(Long id, String email, String password, Double bal) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.balance = bal;

	}
	public accountEntity()
	{

	}
}
