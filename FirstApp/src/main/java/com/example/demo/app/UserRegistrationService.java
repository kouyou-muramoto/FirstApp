package com.example.demo.app;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
	
	@Autowired
	private UserRepository repository;
	
	@Transactional
	public void registerUser(AuthenticatedUser user) {
		int result=0;
		result += repository.registerUser(user);
		
		if(result !=1) {
			throw new RuntimeException();
		}
	}

}
