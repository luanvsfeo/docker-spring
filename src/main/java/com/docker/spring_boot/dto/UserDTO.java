package com.docker.spring_boot.dto;

import com.docker.spring_boot.domain.User;

public class UserDTO {

	private String email;

	private String password;


	public UserDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}


	public boolean validForLogin(){
		return this.password != null && this.email != null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User convertToUser(){
		return new User(this.email,this.password);
	}
}