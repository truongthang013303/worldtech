package com.example.demo1.API.admin;

import com.example.demo1.dto.DataTable;
import com.example.demo1.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.RoleDTO;
import com.example.demo1.service.IRoleService;

import java.util.List;
import java.util.Optional;

@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
@RestController(value ="roleAPIOfAdmin")
@RequestMapping(value = "api/role",produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleAPI 
{
	@Autowired
	private IRoleService roleService;
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Optional<Integer> length, @RequestParam(name = "orderCol") Optional<String> sort, @RequestParam(name = "sortDir") Optional<String> direction, @RequestParam Optional<String> search)
	{
		if(search.isPresent()&&search!=null&&!search.get().equals(""))
		{
			return ResponseEntity.ok(new DataTable(draw, 0,0, roleService.findByNameContaining(search.get())));

		}
		Integer page = start/ length.orElse(3);

		Page<RoleDTO> dataPerPage = roleService.findByPage(PageRequest.of(page, length.orElse(3), Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("id"))));
		DataTable returnClient = new DataTable(draw, (int)(dataPerPage.getTotalElements()), (int)(dataPerPage.getTotalElements()), dataPerPage.getContent());
		return ResponseEntity.ok(returnClient);
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
	public ResponseEntity<?> delete(@RequestBody long[] ids)
	{
		return roleService.deleteRoles(ids);
	}
}
