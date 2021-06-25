package com.docker.spring_boot.domain;

import com.docker.spring_boot.dto.UserDTO;
import com.docker.spring_boot.util.ConversionUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private String password;

	private boolean enabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "permission_id", referencedColumnName = "id"))
	private Collection<Permission> roles;

	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(Long id, String email, String password, boolean enabled) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}

	public User(Long id, String email, String password, boolean enabled, Collection<Permission> roles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public void changePassword() {
		this.password = ConversionUtil.encode(this.password);
	}

	public boolean validForLogin(){
		return this.password != null && this.email != null;
	}

	public void create(Permission permission){
		this.setRoles(Collections.singleton(permission));
		this.changePassword();
	}

	public UserDTO convertToDTO(){
		return new UserDTO(this.email,this.password);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Permission> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Permission> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
