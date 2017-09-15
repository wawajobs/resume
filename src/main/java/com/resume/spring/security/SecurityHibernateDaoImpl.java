package com.resume.spring.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.resume.dto.UserInfo;
import com.resume.service.UserService;


@Transactional(rollbackFor=Throwable.class, readOnly=true)
public class SecurityHibernateDaoImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userFromUserQuery = loadUser(username);
		if (userFromUserQuery == null){
			throw new UsernameNotFoundException("Username "+username+" not found");
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.addAll(loadUserAuthorities(userFromUserQuery.getUsername()));
//        if (grantedAuthorities.size() == 0) {
//            throw new UsernameNotFoundException("User "+userFromUserQuery.getUsername()+" has no GrantedAuthority");
//        }
	    userFromUserQuery.setAuthorities(grantedAuthorities);
		return userFromUserQuery;
	}
	
	protected List<GrantedAuthority> loadUserAuthorities(String username) {
		//List<Role> roles = getHibernateTemplate().find(HQL_LOAD_AUTHORITIES, username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//		}
		return authorities;
	}
	
	
	protected User loadUser(String username) {
		UserInfo userInfo = userService.queryByUsername(username);
		if (userInfo == null) {
			return null;
		}
		User user = new User(userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getNickname(), userInfo.isEnabled());
		user.setEmail(userInfo.getEmail());
		user.setId(userInfo.getId());
		user.setRole(userInfo.getRole());
		return user;
	}

}
