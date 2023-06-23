package com.example.demo1.security;

import java.util.*;

import com.example.demo1.dto.AppUser;
import com.example.demo1.entity.PrivilegeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.demo1.entity.RoleEntity;
import com.example.demo1.entity.UserEntity;
import com.example.demo1.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findOneByUserNameAndStatus(username, 1);
		if(userEntity==null)
		{
			throw new UsernameNotFoundException("cloud no find user");
		}
/*		MyUser myUser = new MyUser(userEntity.getUserName(), userEntity.getPassword(),userEntity.getFullName(),
				true, true, true, true, getAuthorities(userEntity.getRoles()));*/

		Set<PrivilegeEntity> priviInEveryRole = new HashSet<>();
		for(RoleEntity i:userEntity.getRoles())
		{
			priviInEveryRole.addAll(i.getPrivileges());
		}
		AppUser myUser = new AppUser(userEntity.getId(), userEntity.getUserName(), userEntity.getPassword(),true, true, true, true, getAuthorities(userEntity.getRoles()), userEntity.getRoles(), priviInEveryRole, userEntity.getInterest());
		return myUser;
/*		List<GrantedAuthority> authorities = new ArrayList<>();
		for (RoleEntity role: userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}

		MyUser myUser = new MyUser(userEntity.getUserName(), userEntity.getPassword(),userEntity.getFullName(), 
				true, true, true, true, authorities);*/
		//myUser.setFullName(userEntity.getFullName());
	}

	//Principal datatype depend on return datatype of loadUserByUsername
/*	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findOneByUserNameAndStatus(username, 1);
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (RoleEntity role: userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		return new User("admin","$2a$10$Zd9WvqBrQaJLL3jAxU6woOg231FXrtHChrnnOIokQK0u2oPlMlW1G",authorities);
	}*/
	private Collection<? extends GrantedAuthority> getAuthorities(
			Collection<RoleEntity> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	//Get all Privileges and Roles, add to List<String> privileges
	private List<String> getPrivileges(Collection<RoleEntity> roles) {

		List<String> privileges = new ArrayList<>();
		List<PrivilegeEntity> collection = new ArrayList<>();
		for (RoleEntity role : roles) {
			privileges.add(role.getCode());
			collection.addAll(role.getPrivileges());
		}
		for (PrivilegeEntity item : collection) {
			privileges.add(item.getCode());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
}
