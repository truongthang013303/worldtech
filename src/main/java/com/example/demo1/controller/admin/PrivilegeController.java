package com.example.demo1.controller.admin;

import com.example.demo1.dto.PrivilegeDTO;
import com.example.demo1.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "privilegeControllerOfAdmin")
@RequestMapping("/quantri/privilege")
@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
public class PrivilegeController
{
    @Autowired
    IPrivilegeService privilegeService;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView homePrivilege()
    {
        ModelAndView mav = new ModelAndView("/admin/Privilege/danhsach");
        return mav;
    }

    @RequestMapping(value = {"/chinhsua"}, method = RequestMethod.GET)
    public ModelAndView showEditForm(@RequestParam(name = "id", required = false) String id)
    {
        ModelAndView mav = new ModelAndView("/admin/Privilege/edit");
        PrivilegeDTO model = new PrivilegeDTO();
        if(id!=null)
        {
            model= privilegeService.findOneById(Long.parseLong(id));
        }
        mav.addObject("model",model);
        return mav;
    }
}
