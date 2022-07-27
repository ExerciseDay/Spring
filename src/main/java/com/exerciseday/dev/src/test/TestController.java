package com.exerciseday.dev.src.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public String hello(@RequestParam(defaultValue = "1") Integer number){
        if(number == 1){
            log.info("/test/log 호출 성공 ################");
        } 
        else if(number == -1){
            log.error("/test/log 에러 호출 성공 ##############");
        }
        else if(number == 0){
            log.warn("/test/log 경고 호출 성공 ################# ");
        }
        return "<h1>log test page</h1>";
    }
}
