package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo1.converter.UserConverter;
import com.example.demo1.dto.UserDTO;
import com.example.demo1.entity.UserEntity;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.service.IUserService;

@Service
public class UserService implements IUserService
{
	private static final Logger logger = LogManager.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@Override
	public List<UserDTO> findAll(Pageable pageable) 
	{
		List<UserEntity> allUserEnity = userRepository.findAll(pageable).getContent();
		List<UserDTO> allUserDTO = new ArrayList<UserDTO>();
		for (UserEntity userEntity : allUserEnity) 
		{
			UserDTO userDTO = userConverter.toDto(userEntity);
			allUserDTO.add(userDTO);
		}
		return allUserDTO;
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> allUserDTO = new ArrayList<UserDTO>();
		List<UserEntity> allUserEnity = userRepository.findAll();
		for (UserEntity userEntity : allUserEnity)
		{
			UserDTO userDTO = userConverter.toDto(userEntity);
			allUserDTO.add(userDTO);
		}
		return allUserDTO;
	}

	@Override
	public int getTotalItem() 
	{
		return (int)userRepository.count();
	}

	@Override
	public UserDTO findById(long id) 
	{
		UserEntity userEntity = userRepository.findOneById(id);
		return userConverter.toDto(userEntity);
	}

	@Override
	@Transactional
	public UserDTO saveOrUpdate(UserDTO dto) 
	{
		UserEntity userEntity = new UserEntity();
	
		if(dto.getId()!=null) 
		{
			UserEntity oldUser = userRepository.findOneById(dto.getId());
			userEntity = userConverter.toEntity(oldUser,dto);	
		}
		else if(userRepository.existsByUserName(dto.getUserName())==false)//userRepository.findExistByUserName(dto.getUserName())==0
		{
				userEntity = userConverter.toEntity(dto);	
		}
		else
		{
			System.out.println("ten dang nhap da ton tai");
			return null;
		}
		UserEntity savedUserEntity = userRepository.save(userEntity);
		return userConverter.toDto(savedUserEntity);
	}
	
	@Override
	@Transactional
	public void delete(long[] ids) 
	{
		for (long id : ids) 
		{
			logger.warn("Deleted user with username:" + id);	
			userRepository.deleteById(id);
		}
		
	}
	
	@Override
	public UserEntity findOneByUserNameAndStatus(String username)
	{
		UserEntity user = userRepository.findOneByUserNameAndStatus(username, 1);
		return user;
	}
}
