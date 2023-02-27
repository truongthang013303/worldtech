package com.example.demo1.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo1.converter.UserConverter;
import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.dto.NewDTO;
import com.example.demo1.dto.UserDTO;
import com.example.demo1.entity.UserEntity;
import com.example.demo1.service.ICategoryService;
import com.example.demo1.service.INewService;
import com.example.demo1.service.IUserService;
import com.example.demo1.utils.MessageUtil;

@Controller(value = "newControllerOfAdmin")
public class NewController 
{
	@Autowired
	private INewService newService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private IUserService userService;

	@RequestMapping(value = {"/quantri/home","/quantri"}, method = RequestMethod.GET)
	public ModelAndView homePage(@RequestParam(required = false) String catecode, @RequestParam(required = false) Optional<Long> cateid, @RequestParam(name = "page", required = false) Optional<Integer> page, 
			@RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort, HttpServletRequest request) 
	{
		ModelAndView mav = new ModelAndView("/admin/homepage");
		Map<CategoryDTO, String> map = new HashMap<>();
		
		if(catecode!=null)
		{
			NewDTO model = new NewDTO();
			int pageCV=page.orElse(1);
			int limitCV=limit.orElse(2);
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
			List<NewDTO> listNew = newService.findAllByCategoryCode(catecode,pageable);
			model.setListResult(listNew);
			
			String rqMess = request.getParameter("message");
			if ( rqMess != null) 
			{
				Map<String, String> message = messageUtil.getMessage(rqMess);
				model.setMessage(message.get("message"));
				model.setAlert(message.get("alert"));
			}
			model.setTotalItem(newService.getTotalItemByCategory_id(cateid.get()));
			model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getLimit()));
			mav.addObject("sort",sort);
			mav.addObject("model", model);
			mav.addObject("catecode", catecode);
			mav.addObject("cateid", cateid.get());
			mav.setViewName("/admin/New/danhsach");
		}
		else
		{
			List<CategoryDTO> allCategory =  categoryService.findAll();
			for (CategoryDTO categoryDTO : allCategory) 
			{
				int count = newService.countByCategory_id(categoryDTO.getId());
				map.put(categoryDTO, Integer.toString(count));
			}		
			mav.addObject("categories", allCategory);
			mav.addObject("map", map);
		}
		return mav;
	}
	@RequestMapping(value = {"/quantri/baiviet/danhsach","/quantri/baiviet"}, method = RequestMethod.GET)
	public ModelAndView newList(@RequestParam(name = "page", required = false) Optional<Integer> page, 
	@RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort, HttpServletRequest request) 
	{
		NewDTO model = new NewDTO();
		int pageCV=page.orElse(1);
		int limitCV=limit.orElse(2);
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
		ModelAndView mav = new ModelAndView("/admin/New/danhsach");
		Pageable pageable = PageRequest.of(pageCV-1, limitCV, sort2);
		model.setListResult(newService.findAll(pageable));
		String rqMess = request.getParameter("message");
		if ( rqMess != null) 
		{
			Map<String, String> message = messageUtil.getMessage(rqMess);
			model.setMessage(message.get("message"));
			model.setAlert(message.get("alert"));
		}
		model.setTotalItem(newService.getTotalItem());
		model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getLimit()));
		mav.addObject("sort",sort);
		mav.addObject("model", model);
		return mav;
	}

	@RequestMapping(value = {"/quantri/baiviet/chinhsua"}, method = RequestMethod.GET)
	public ModelAndView showFormEditNew(@RequestParam(name = "id", required = false) String id, HttpServletRequest request) 
	{
		NewDTO model = new NewDTO();
		ModelAndView mav = new ModelAndView("/admin/New/edit");
		if(id!=null)
		{
			model= newService.findById(Long.parseLong(id));
		}
		List<CategoryDTO> listCateDTO = categoryService.findAll();
		mav.addObject("listCate", listCateDTO);
		String rqMess = request.getParameter("message");
		if ( rqMess != null) 
		{
			Map<String, String> message = messageUtil.getMessage(rqMess);
			model.setMessage(message.get("message"));
			model.setAlert(message.get("alert"));
		}
		mav.addObject("model", model);
		return mav;
	}	
	@RequestMapping(value = "/finduser", method = RequestMethod.GET)
	public String showFormSearchUser(Model model) 
	{
		UserDTO userdto = new UserDTO();
		model.addAttribute("model",userdto);
		return "/admin/User/findUserByUsername";
	}	
	@RequestMapping(value = "/finduser", method = RequestMethod.POST)
	public String resolveUser(Model model, @RequestParam String userName) 
	{
		UserDTO userdto = new UserDTO();
		UserEntity userEn = userService.findOneByUserNameAndStatus(userName); //.getParameter("userName")
		if(userEn!=null)
		{
			userdto = userConverter.toDto(userEn);
		}
		model.addAttribute("model", userdto);
		return "/admin/User/findUserByUsername";
	}
}
