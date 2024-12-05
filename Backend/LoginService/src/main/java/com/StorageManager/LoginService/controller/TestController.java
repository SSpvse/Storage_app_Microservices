package com.StorageManager.LoginService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/health")
    public ResponseEntity<String> testHealth() {
        System.out.println("blalbalblalb");
        return ResponseEntity.ok("Service is up and running");
    }
}
