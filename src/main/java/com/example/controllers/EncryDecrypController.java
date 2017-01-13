package com.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class EncryDecrypController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String hello(){
        return "Hello World!!!!";
    }

}
