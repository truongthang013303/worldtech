package com.example.demo1.API.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.RoleDTO;
import com.example.demo1.service.IRoleService;

import java.util.List;

@RestController(value ="roleAPIOfAdmin")
@RequestMapping("api/role")
public class RoleAPI 
{
	@Autowired
	private IRoleService roleService;

	@GetMapping
	public List<RoleDTO> findAll()
	{
		return roleService.findAll();
	}
	@PostMapping
	public RoleDTO create(@RequestBody RoleDTO RoleDTO) 
	{
		return roleService.saveOrUpdate(RoleDTO);
	}
	@PutMapping
	public RoleDTO update(@RequestBody RoleDTO RoleDTO) 
	{
		return roleService.saveOrUpdate(RoleDTO);
	}
	@DeleteMapping
	public void delete(@RequestBody long[] ids) 
	{
		roleService.delete(ids);
	}
}
