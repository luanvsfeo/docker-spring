package com.docker.spring_boot.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Permission implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	public Permission(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Permission() {
	}

	public Permission(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name;
	}
}
