package com.example.demo1.API.admin;

import com.example.demo1.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value ="CommonAPI")
@RequestMapping("api")
public class CommonAPI {
    @Autowired
    private ICommonService commonService;

    @GetMapping("/dashboard")
    public ModelMap dashBoard_CommonAPI()
    {
        ModelMap mm = new ModelMap();
        mm =commonService.dashBoard_CommonService();
        return mm;
    }
}
