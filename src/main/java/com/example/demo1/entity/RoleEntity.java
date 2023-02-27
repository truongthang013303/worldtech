package com.example.demo1.entity;


import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private String code;

	@ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_privilege"
			,joinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id")
			,inverseJoinColumns = @JoinColumn(name = "privilegeid", referencedColumnName = "id"))
	private Collection<PrivilegeEntity> privileges=new LinkedHashSet<PrivilegeEntity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Collection<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserEntity> users) {
		this.users = users;
	}

	public Collection<PrivilegeEntity> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<PrivilegeEntity> privileges) {
		this.privileges = privileges;
	}
}
