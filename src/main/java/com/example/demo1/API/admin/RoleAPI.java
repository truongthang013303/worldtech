package com.example.demo1.API.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.RoleDTO;
import com.example.demo1.service.IRoleService;

import java.util.List;

@RestController(value ="roleAPIOfAdmin")
@RequestMapping(value = "api/role",produces = MediaType.APPLICATION_JSON_VALUE)
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
	public RoleDTO create(@RequestBody RoleDTO roleDTO)
	{
		return roleService.saveOrUpdate(roleDTO);
	}
	@PutMapping
	public RoleDTO update(@RequestBody RoleDTO roleDTO)
	{
		return roleService.saveOrUpdate(roleDTO);
	}
	@DeleteMapping
	public void delete(@RequestBody long[] ids) 
	{
		roleService.delete(ids);
	}
}
