package com.example.WebSecurity.securitconfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.WebSecurity.entity.Role;
import com.example.WebSecurity.entity.User;
import com.example.WebSecurity.repository.UserRepository;



@Service("myAppUserDetailsService")
public class MyAppUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User activeUserInfo = userRepository.findByEmail(userName);
		GrantedAuthority authority = null ;
		for(Role role:activeUserInfo.getRoles())
		{
		 authority = new SimpleGrantedAuthority("USER");
		}
		UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(activeUserInfo.getEmail(),
				activeUserInfo.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
	/* private Set < ? extends GrantedAuthority > mapRolesToAuthorities(Set < Role > roles) {
	        User role;
			return roles.stream()
	            .map(role - > new SimpleGrantedAuthority(role.getName()))
	            .collect(Collectors.toList());
	    }*/
}

