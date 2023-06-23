package com.example.demo1.API.admin;

import javax.validation.Valid;

import com.example.demo1.dto.DataTable;
import com.example.demo1.dto.NewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.service.ICategoryService;
import java.util.List;
import java.util.Optional;

/*@CrossOrigin(origins = "*")*/
//@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
@RestController(value ="categoryAPIOfAdmin")
@RequestMapping("api/category")
public class CategoryAPI 
{
	@Autowired
	private ICategoryService categoryService ;

	/*@GetMapping
	public List<CategoryDTO> findAll()
	{
		return categoryService.findAll();
	}*/
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Integer draw, @RequestParam Integer start, @RequestParam Optional<Integer> length, @RequestParam(name = "orderCol") Optional<String> sort, @RequestParam(name = "sortDir") Optional<String> direction, @RequestParam Optional<String> search)
	{
		if(search.isPresent()&&search!=null&&!search.get().equals(""))
		{
			return ResponseEntity.ok(new DataTable(draw, 0,0, categoryService.findByNameContaining(search.get())));

		}
		Integer page = start/ length.orElse(3);
		Pageable pageable = PageRequest.of(page, length.orElse(3), Sort.by(Sort.Direction.fromString(direction.orElse("DESC")), sort.orElse("id")));
		Page<CategoryDTO> dataPerPage;
		DataTable returnClient;
		dataPerPage = categoryService.findByPage(pageable);
		returnClient = new DataTable(draw, (int)(dataPerPage.getTotalElements()), (int)(dataPerPage.getTotalElements()), dataPerPage.getContent());
		return ResponseEntity.ok(returnClient);
	}

	@GetMapping("/dashboard")
	public List<CategoryDTO> dashBoard()
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
