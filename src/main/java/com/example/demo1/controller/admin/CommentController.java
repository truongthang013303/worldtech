package com.example.demo1.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "commentControllerOfAdmin")
@RequestMapping("quantri/comment")
@PreAuthorize("isAuthenticated()")
public class CommentController {
    @GetMapping
    public String index(){
        return "/admin/Comment/danhsach";
    }
}
