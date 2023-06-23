package com.example.demo1.service;

import java.util.List;
import java.util.Optional;

import com.example.demo1.entity.PostRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.UserDTO;
import com.example.demo1.entity.UserEntity;
import org.springframework.http.ResponseEntity;

public interface IUserService 
{
	List<UserDTO> findAll();

    UserEntity rating(Long postid, Long userid, Integer rating);

    List<UserDTO> findAll(Pageable pageable);
	Long getTotalItem();
	UserDTO findById(long id);
	UserDTO saveOrUpdate(UserDTO dto);
	void delete(long[] ids);
	UserEntity findOneByUserNameAndStatus(String username);
	UserDTO changeStatusUser(Optional<Long> id, Optional<Integer> status);
	ResponseEntity<?> changeStatusUserResponseEntity(Optional<Long> id, Optional<Integer> status);

	List<UserDTO> findByUserNameContaining(String username);

	Page<UserDTO> findByPage(Pageable pageable);

	Page<UserDTO> convertPageEntityToPageDTO(Page<UserEntity> entities);

    Page<UserDTO> findByRolesIn(Long roleid, Pageable pageable);
}
