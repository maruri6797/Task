package com.dmm.task.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.dmm.task.data.entity.Users;

public class AccountUserDetails implements UserDetails {
	private Users user;
	
	public AccountUserDetails(Users user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_" + user.getRoleName());
	}
	
}
