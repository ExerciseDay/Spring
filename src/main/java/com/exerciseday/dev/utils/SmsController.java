package com.exerciseday.dev.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//import com.exerciseday.dev.config.AWSconfig;


import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SnsResponse;

import java.util.Random;
/*
@Slf4j
@RestController
public class SmsController{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    AWSconfig awsConfig;
    SmsService smsService;

    public SmsController(AWSconfig awsSconfig, SmsService smsService){
        this.awsConfig = awsSconfig;
        this.smsService = smsService;
    }



    @PostMapping("/certify")
    public String certifyByPhone(@RequestBody String phone) {
        SnsClient snsClient = smsService.getSnsClient();
        String code = "";
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for(int i = 0 ; i < 6 ; i++){
            code+=Integer.toString(random.nextInt(9));
        }

        String message = "하루운동 본인인증 [" + code + "] 입니다.";
        String phoneNumber = "+82" + phone;
        try {
            PublishRequest request = PublishRequest.builder()
                .message(message)
                .phoneNumber(phoneNumber)
                .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            //System.exit(1);
        }
        logger.info("/certify 호출 성공 code ["+code+"] ####################");
        return code;
    }




    private ResponseStatusException getResponseStatusException(SnsResponse response) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, response.sdkHttpResponse().statusText().get()
        );
    }
}
*/