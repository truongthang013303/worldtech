package com.example.demo1.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullName;

	@Column
	private Integer status;

	@OneToOne
	@JoinColumn(name = "interest", referencedColumnName = "id")
	private CategoryEntity interest;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
	private Collection<RoleEntity> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =FetchType.LAZY)
	Set<PostRating> ratings;

	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
	private Collection<CommentEntity> comments;
}
