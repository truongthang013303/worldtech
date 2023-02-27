package com.example.demo1.controller.admin;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo1.dto.RoleDTO;
import com.example.demo1.service.IRoleService;
import com.example.demo1.utils.MessageUtil;

@Controller(value = "roleControllerOfAdmin")
public class RoleController 
{
	@Autowired
	private IRoleService roleService;
	@Autowired
	private MessageUtil messageUtil;
	@RequestMapping(value = {"/quantri/role/danhsach","/quantri/role"}, method = RequestMethod.GET)
	public ModelAndView roleListPage(@RequestParam(name = "page", required = false) Optional<Integer> page, 
	@RequestParam(name = "limit", required = false) Optional<Integer> limit,@RequestParam(name="sort", required = false) String sort, HttpServletRequest request) 
	{
		RoleDTO model = new RoleDTO();
		int pageCV=page.orElse(1);
		int limitCV=limit.orElse(3);
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
		ModelAndView mav = new ModelAndView("/admin/Role/danhsach");
		Pageable pageable = PageRequest.of(pageCV-1, limitCV, sort2);
		model.setListResult(roleService.findAll(pageable));
		String rqMess = request.getParameter("message");
		if ( rqMess != null) 
		{
			Map<String, String> message = messageUtil.getMessage(rqMess);
			model.setMessage(message.get("message"));
			model.setAlert(message.get("alert"));
		}
		model.setTotalItem(roleService.getTotalItem());
		model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getLimit()));
		mav.addObject("sort",sort);
		mav.addObject("model", model);
		return mav;
	}
	
	@RequestMapping(value = {"/quantri/role/chinhsua"}, method = RequestMethod.GET)
	public ModelAndView showFormEdit(@RequestParam(name = "id", required = false) String id, HttpServletRequest request) 
	{
		RoleDTO model = new RoleDTO();
		ModelAndView mav = new ModelAndView("/admin/Role/edit");
		if(id!=null)
		{
			model= roleService.findById(Long.parseLong(id));
		}
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
}
