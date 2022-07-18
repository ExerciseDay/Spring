package com.exerciseday.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//연결 확인용 컨트롤러
@RestController
public class SecurityController {
    @RequestMapping(value = "/api/sec", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String index() {
        return "exercise day";
    }
}
