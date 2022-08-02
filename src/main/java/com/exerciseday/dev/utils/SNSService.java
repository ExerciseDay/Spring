package com.exerciseday.dev.utils;

import java.util.Random;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Service
public class SNSService {


    public String sendSMS(String phone){
        String phoneNumber = "+82" + phone;
        String code = "";
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for(int i = 0 ; i < 6 ; i++){
            code+=Integer.toString(random.nextInt(9));
        }


        String message = "하루운동 본인인증 [" + code + "] 입니다.";

        SnsClient snsClient = SnsClient.builder()
        .region(Region.AP_NORTHEAST_1)
        .credentialsProvider(ProfileCredentialsProvider.create())
        .build();

        try{
            PublishRequest request = PublishRequest.builder()
            .message(message)
            .phoneNumber(phoneNumber)
            .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId()+" Message sent. Status was " + result.sdkHttpResponse().statusCode());
        } catch (SnsException e){
            System.err.println(e.awsErrorDetails().errorMessage());
        }

        snsClient.close();
        return code;
    }

    
}
