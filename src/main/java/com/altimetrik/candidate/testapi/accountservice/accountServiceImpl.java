package com.altimetrik.candidate.testapi.accountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altimetrik.candidate.testapi.accountentity.accountEntity;
import com.altimetrik.candidate.testapi.accountentity.accountModel;
import com.altimetrik.candidate.testapi.accountrepository.accountRepository;
import com.altimetrik.candidate.testapi.entity.userEntity;
import com.altimetrik.candidate.testapi.exception.userException;
import com.altimetrik.candidate.testapi.repository.userRepository;

/**
 * Account Service Class to define Business logic methods implemented in the Interface accountService.
 */
@Service
public class accountServiceImpl implements accountService {

	@Autowired
	private accountRepository accRepo;
	
	@Autowired
	private userRepository uRepo;
	
	@Override
	public accountEntity createAccount(accountModel amodel, String email) throws userException {
		accountEntity accEntity = new accountEntity();
		Optional<userEntity> found = uRepo.findByEmail(email);
		if(!amodel.getEmail().equals(email))
		{
			throw new userException(userException.EmailMismatch(email));
		}
		else if (!found.isPresent()) {
			throw new userException(userException.NotFoundException(email));
		} else {
			Double monthsal = found.get().getMonthlysal();
			Double monthexp = found.get().getMonthlyexp();
			if (monthsal-monthexp>=1000) {
				amodel.setBalance(monthsal-monthexp);
				BeanUtils.copyProperties(amodel, accEntity);
				accEntity.setId(found.get().getId());
				return accRepo.save(accEntity);
			}
			else {
				throw new userException(userException.BalanceLow(monthsal-monthexp));
			}
		}
	}

	@Override
	public List<accountEntity> getAllAccounts() throws userException {
		List<accountEntity> allUsers = accRepo.findAll();
		if (allUsers.isEmpty()) {
			throw new userException(userException.EmptyDB());
		}
		else {
			return new ArrayList<>(allUsers);
		}
	}

}
