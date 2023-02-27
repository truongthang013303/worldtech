package com.example.demo1.API.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.UserDTO;
import com.example.demo1.service.IUserService;

@RestController(value ="userAPIOfAdmin")
@RequestMapping("api/user")
public class UserAPI {
	@Autowired
	private IUserService userService;

	@GetMapping
	public List<UserDTO> findAll()
	{
		return userService.findAll();
	}
	@PostMapping
	public UserDTO createNew(@RequestBody UserDTO userDTO) 
	{
		if(userDTO.getRoleCode().isEmpty())
		{userDTO.setRoleCode(Arrays.asList("ROLE_USER"));}
		userDTO.setStatus(1);
		return userService.saveOrUpdate(userDTO);
	}
	@DeleteMapping
	public void deleteNew(@RequestBody long[] ids) 
	{
		userService.delete(ids);
	}
	
	@PutMapping
	public UserDTO updateUser(@RequestBody UserDTO userDTO) 
	{
		userDTO.setStatus(1);
		return userService.saveOrUpdate(userDTO);
	}
}
