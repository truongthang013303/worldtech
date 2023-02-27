package com.example.demo1.controller.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo1.dto.NewDTO;
import com.example.demo1.dto.UserDTO;
import com.example.demo1.service.ICategoryService;
import com.example.demo1.service.INewService;
import com.example.demo1.utils.MessageUtil;
import org.springframework.web.servlet.View;

@Controller(value = "homeControllerOfWeb")
public class HomeController 
{
	@Autowired
	private INewService newService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private MessageUtil messageUtil;
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String indexPageRedirect()
//	{
//		return "redirect:/home";
//	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String pageNotFound()
	{
		return "403";
	}
	
	@RequestMapping(value = {"/home","/",""}, method = RequestMethod.GET)
	public ModelAndView homePage(@RequestParam(name = "page", required = false) Optional<Integer> page,
			@RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort)
	{
		NewDTO model = new NewDTO();
		ModelAndView mav = new ModelAndView("/web/home");	
		model.setListResult(newService.findAll(PageRequest.of(page.orElse(0),limit.orElse(2))));
		model.setPage(page.orElse(0));
		model.setTotalPage((int) Math.ceil((double) newService.getTotalItem() / limit.orElse(2)));
		mav.addObject("categories",categoryService.findAll());
		mav.addObject("model",model);
		return mav;
	}
	
	@RequestMapping(value = "/category/{code}", method = RequestMethod.GET)
	public ModelAndView newsSortByCate(@PathVariable(name = "code", required = false) String code, @RequestParam(name = "page", required = false) Optional<Integer> page,
			@RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort) 
	{
		ModelAndView mav = new ModelAndView("/web/home");
		NewDTO model = new NewDTO();
		
		int pageCV=page.orElse(1);
		int limitCV=limit.orElse(4);
		model.setLimit(limitCV);
		model.setPage(pageCV);
		String sortField="";
		String sortDir="";
		if(sort!=null) 
		{
			sortField = StringUtils.substringBefore(sort, "-");
			sortDir = StringUtils.substringAfter(sort, "-");
		}else {
			sortField="createdDate";
			sortDir="DESC";
			sort=sortField+"-"+sortDir;
		}
		Sort sort2 = Sort.by(sortField);
		sort2 = sortDir.equals("ASC") ? sort2.ascending() : sort2.descending();
		Pageable pageable = PageRequest.of(pageCV-1, limitCV, sort2);
		List<NewDTO> listNewDTO = newService.findAllByCategoryCode(code, pageable);		
		model.setListResult(listNewDTO);				
		model.setTotalItem(listNewDTO.size());
		model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getLimit()));
		
		mav.addObject("model", model);
		mav.addObject("sortCategory",sort);
		mav.addObject("category",code);
		mav.addObject("categories",categoryService.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/baiviet/{id}", method = RequestMethod.GET)
	public ModelAndView newDetail(@PathVariable(name = "id", required = false) long id) 
	{
		ModelAndView mav = new ModelAndView("/web/newDetail");
		NewDTO model =  newService.findById(id);		
		mav.addObject("model",model);
		mav.addObject("categories",categoryService.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(name = "keyword", required = false) String keyword) 
	{
		NewDTO model = new NewDTO();
		ModelAndView mav = new ModelAndView("/web/home");
		List<NewDTO> listNewSearch =  newService.findByTitleContaining(keyword);
		model.setListResult(listNewSearch);
		mav.addObject("model",model);
		mav.addObject("categories",categoryService.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView regisPage(@RequestParam(name = "message",required = false) String rqMess) 
	{
		ModelAndView mav = new ModelAndView("/register");
		UserDTO userDTO = new UserDTO();
		//String rqMess = request.getParameter("message");
		if ( rqMess != null) 
		{
			Map<String, String> message = messageUtil.getMessage(rqMess);
			userDTO.setMessage(message.get("message"));	
			userDTO.setAlert(message.get("alert"));
		}
		mav.addObject("model", userDTO);
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(name = "error",required = false) String error) 
	{
		ModelAndView mav = new ModelAndView("/login");
		if(error!=null)
		{
			mav.addObject("error", error);
		}
		return mav;
	}


	@RequestMapping(value = "/abc", method = RequestMethod.GET)
	public ModelAndView newLay()
	{
		ModelAndView mav = new ModelAndView("/web/Home/TestNewLay");
		return mav;
	}
	@RequestMapping(value = "/world", method = RequestMethod.GET)
	public ModelAndView world()
	{
		ModelAndView mav = new ModelAndView("/web/Home/WorldHome");
		mav.addObject("categories",categoryService.findAll());
		mav.addObject("news", newService.findAll(PageRequest.of(0,4)));
		return mav;
	}
}
