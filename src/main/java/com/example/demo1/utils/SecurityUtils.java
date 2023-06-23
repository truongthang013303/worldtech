package com.example.demo1.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.demo1.dto.AppUser;
import org.apache.hadoop.mapreduce.v2.app.webapp.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtils {
	@Autowired
	PasswordEncoder passwordEncoder;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public AppUser getPrincipal() {
		AppUser appUser = (AppUser) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
		return appUser;
	}

	public String bcryptEncryptor(String plainText) {
		return passwordEncoder.encode(plainText.trim());
	}
	public Boolean doPasswordsMatch(String rawPassword, String encodedPassword){
		return passwordEncoder.matches(rawPassword.trim(), encodedPassword);
	}
}
