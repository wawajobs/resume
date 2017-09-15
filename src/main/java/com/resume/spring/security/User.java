package com.resume.spring.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = -1030276914673355966L;
	
	private long id;
	private String username;
	private String password;
	private String nickname;
	private String email;
	private boolean enabled;
	private String role;
	private Collection<GrantedAuthority> authorities;
	
//	private List<MenuItem> menus ;
	
	public User(String username, String password, String nickname, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.enabled = enabled;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities; 
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

//	public List<MenuItem> getMenus() {
//		return menus;
//	}
//
//	public void setMenus(List<MenuItem> menus) {
//		this.menus = menus;
//	}

}
