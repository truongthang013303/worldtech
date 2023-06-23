package com.example.demo1.API.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController(value ="UploadFileAPI")
@RequestMapping("/ckfinder")
public class UploadFileAPI {
    @PostMapping
    public ResponseEntity<?> getFile(@RequestParam(value = "upload", required = false) MultipartFile multipartFile){
        MultipartFile file = multipartFile;
        System.out.println(file);
        return null;
    }
    @PostMapping("/upload")
    public ResponseEntity<?> ckfinderUploadImg(@RequestPart MultipartFile upload){
        MultipartFile file = upload;
        System.out.println(file);
        return null;
    }
}
