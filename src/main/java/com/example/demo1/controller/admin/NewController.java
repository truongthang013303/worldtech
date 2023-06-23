package com.example.demo1.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import com.example.demo1.service.ICommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
@PreAuthorize("isAuthenticated()")
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

	@Autowired
	private ICommonService commonService;

	@PreAuthorize("hasAuthority('ACCESS_HOMEADMIN') OR hasRole('ROLE_ADMIN')")
	@RequestMapping(value = {"/quantri/home","/quantri"}, method = RequestMethod.GET)
	public ModelAndView homePage(@RequestParam(required = false) String catecode, @RequestParam(required = false) Optional<Long> cateid, @RequestParam(name = "page", required = false) Optional<Integer> page, 
			@RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort, HttpServletRequest request) 
	{
		ModelAndView mav = new ModelAndView("/admin/homepage");
		mav.addObject("dashboard", commonService.dashBoard_CommonService());
		return mav;
	}
/*	@PreAuthorize("hasAuthority('VIEW_POST') OR hasAuthority('VIEW_SELF_POST') OR hasAuthority('PUBLISH_POST') OR hasAuthority('UPDATE_POST') OR hasAuthority('UPDATE_SELF_POST') OR hasAuthority('CREATE_POST') OR hasAuthority('DELETE_POST')")*/
	@PreAuthorize("hasAnyAuthority('VIEW_POST','VIEW_SELF_POST','PUBLISH_POST','UPDATE_POST','UPDATE_SELF_POST','CREATE_POST','DELETE_POST')")
	@RequestMapping(value = {"/quantri/baiviet/danhsach","/quantri/baiviet"}, method = RequestMethod.GET)
	public ModelAndView newList(@RequestParam(name = "page", required = false) Optional<Integer> page, @RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort, HttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView("/admin/New/danhsach");
		return mav;
	}

/*	@PreAuthorize("#id==null?hasAuthority('CREATE_POST'):hasAuthority('UPDATE_POST') OR hasAuthority('UPDATE_SELF_POST') OR hasAuthority('PUBLISH_POST') OR hasAuthority('VIEW_POST') OR hasAuthority('VIEW_SELF_POST')")*/
	@PreAuthorize("#id==null?hasAuthority('CREATE_POST'):hasAnyAuthority('UPDATE_POST','UPDATE_SELF_POST','PUBLISH_POST','VIEW_POST','VIEW_SELF_POST')")
	@PostAuthorize("hasRole('ROLE_ADMIN') OR #id==null OR hasAuthority('PUBLISH_POST') OR hasAuthority('VIEW_POST') OR returnObject.model['model'].createdBy == authentication.principal.username")
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
		mav.addObject("model", model);
/*		String rqMess = request.getParameter("message");
		if ( rqMess != null) 
		{
			Map<String, String> message = messageUtil.getMessage(rqMess);
			model.setMessage(message.get("message"));
			model.setAlert(message.get("alert"));
		}*/
		return mav;
	}	
}
