package com.example.demo1.API.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.NewDTO;
import com.example.demo1.service.INewService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController(value ="newAPIOfAdmin")
@RequestMapping("api/new")
public class NewAPI 
{
	@Autowired
	private INewService newService;

	@GetMapping
	public List<NewDTO> findAll()
	{
		return newService.findAll();
	}

	@GetMapping("/{id}")
	public NewDTO findById(@PathVariable long id)
	{
		return newService.findById(id);
	}

//	@GetMapping("/page")
//	public Page<NewDTO> findByPage(@RequestParam int page)
//	{
//		return newService.findByPage(PageRequest.of(page,2));
//	}

	@GetMapping("/page")
	public Page<NewDTO> findByPage(@RequestParam int page, @RequestParam Optional<String> cateCode)
	{
        if(cateCode.orElse(null)==null || cateCode.get().equalsIgnoreCase("")||cateCode.get().equalsIgnoreCase("undefined"))
        {
            return newService.findByPage(PageRequest.of(page,4));
        }
		return newService.findAllByCategoryCodePage(cateCode.orElse("kinh-doanh"), PageRequest.of(page,4));
	}

//	@GetMapping("/api/new")
//	public ModelAndView findAll()
//	{
//		return new ModelAndView("/Shared/Footer");
//	}
	@PostMapping
	public NewDTO createNew(@RequestBody @Valid NewDTO newDTO) 
	{
		return newService.saveOrUpdate(newDTO);
	}
	@PutMapping
	public NewDTO updateNew(@RequestBody @Valid NewDTO newDTO) 
	{
		return newService.saveOrUpdate(newDTO);
	}
	
	@DeleteMapping
	public void deleteNew(@RequestBody long[] ids) 
	{
		newService.delete(ids);
	}
}
