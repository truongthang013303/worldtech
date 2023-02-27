package com.example.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> 
{
	UserEntity findOneByUserNameAndStatus(String name, int status);
	UserEntity findOneById(long id);		
	boolean existsByUserName(String userName);
}
