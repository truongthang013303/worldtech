package com.example.demo1.API.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.service.ICategoryService;

import java.util.List;

/*@CrossOrigin(origins = "*")*/
@RestController(value ="categoryAPIOfAdmin")
@RequestMapping("api/category")
public class CategoryAPI 
{
	@Autowired
	private ICategoryService categoryService ;

	@GetMapping
	public List<CategoryDTO> findAll()
	{
		return categoryService.findAll();
	}

	/*@CrossOrigin(origins = "http://127.0.0.1:5500")*/
	@PostMapping
	public CategoryDTO create(@RequestBody @Valid CategoryDTO categoryDTO)
	{
		return categoryService.saveOrUpdate(categoryDTO);
	}
	@PutMapping
	public CategoryDTO update(@RequestBody @Valid CategoryDTO categoryDTO) 
	{
		return categoryService.saveOrUpdate(categoryDTO);
	}
	@DeleteMapping
	public void delete(@RequestBody long[] ids) 
	{
		categoryService.delete(ids);
	}
}
