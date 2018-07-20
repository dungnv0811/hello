package com.example.hello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HelloWorldController.BASE_URL)
public class HelloWorldController {
    public static final String BASE_URL = "/hello";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String hello(){
        return "hello world";
    }
}
