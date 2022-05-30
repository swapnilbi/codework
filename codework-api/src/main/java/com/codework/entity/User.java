package com.codework.entity;

import com.codework.enums.Gender;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Document(collection = "User")
public class User implements UserDetails {

	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";

	@Id
	private long id;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private Gender gender;
	private List<String> roles = new ArrayList<>();
	private boolean isActive = Boolean.TRUE;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if(!roles.isEmpty()){
			for(String role:roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
}
