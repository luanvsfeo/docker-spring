package com.docker.spring_boot.service;

import com.docker.spring_boot.domain.Permission;
import com.docker.spring_boot.domain.User;
import com.docker.spring_boot.repository.PermissionRepository;
import com.docker.spring_boot.repository.UserRepository;
import com.docker.spring_boot.util.ConversionUtil;
import com.docker.spring_boot.util.JwtTokenUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final JwtTokenUtil jwtTokenUtil;

	private final PermissionRepository permissionRepository;

	public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, PermissionRepository permissionRepository) {
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
		this.permissionRepository = permissionRepository;
	}

	public boolean isValidToCreate (User user){
		return !userRepository.existsByEmail(user.getEmail()) && user.getEmail() != null;
	}

	public User createUserCustomer(User user){
		user.create(permissionRepository.findByName("CUSTOMER"));
		return userRepository.save(user);
	}

	public String generateToken(User user){
		if(user.validForLogin()){
			User userDb = userRepository.findByEmail(user.getEmail());
			if(userDb != null){
				if(ConversionUtil.matches(user.getPassword(), userDb.getPassword())){
					return jwtTokenUtil.generateToken(userDb);
				}
			}
		}
		return null;
	}


	@EventListener(ApplicationReadyEvent.class)
	public void createIfDbIsEmpty() {

		if(userRepository.count() == 0){

			Permission permissionAdm = permissionRepository.save(new Permission("ADMIN"));
			permissionRepository.save(new Permission("CUSTOMER"));

			User user = new User();
			user.setEmail("admin");
			user.setPassword("123");
			user.setRoles(Collections.singleton(permissionAdm));
			user.changePassword();
			userRepository.save(user);
		}}
}
