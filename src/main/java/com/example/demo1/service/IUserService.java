package com.example.demo1.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.UserDTO;
import com.example.demo1.entity.UserEntity;

public interface IUserService 
{
	List<UserDTO> findAll();
	List<UserDTO> findAll(Pageable pageable);
	int getTotalItem();
	UserDTO findById(long id);
	UserDTO saveOrUpdate(UserDTO dto);
	void delete(long[] ids);
	UserEntity findOneByUserNameAndStatus(String username);
}
