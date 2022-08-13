package com.exerciseday.dev.utils;

import java.util.Random;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.secret.Secret;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsResponse;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Slf4j
@Service
public class SNSService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String sendSMS(String phone){
        //전화번호 -> 국제번호 변경
        String phoneNumber = "+82" + phone;

        //인증번호 생성
        String codes = "";
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for(int i = 0 ; i < 6 ; i++){
            codes+=Integer.toString(random.nextInt(9));
        }
        String message = "하루운동 본인인증 [" + codes + "] 입니다.";

        /*
        //SNS 접속
        SnsClient snsClient = SnsClient.builder()
        .region(Region.AP_NORTHEAST_1)
        .credentialsProvider(ProfileCredentialsProvider.create())
        .build();
        */
        
        /*
        // 토픽 구독
        try{
            SubscribeRequest subscribeRequest = SubscribeRequest.builder()
            .protocol("sms")
            .endpoint(phoneNumber)
            .returnSubscriptionArn(true)
            .topicArn(Secret.SNS_TOPIC_ARN)
            .build();

            SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
            System.out.println("Subscription ARN: " + subscribeResponse.subscriptionArn() + "\n\n Status is " + subscribeResponse.sdkHttpResponse().statusCode());
        }catch (SnsException e){
            System.err.println(e.awsErrorDetails().errorMessage());
        }
*/
        // 메세지 전송
        /*
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
        */
/* 
        // 토픽 구독 목록
        try{
            ListSubscriptionsRequest listSubscriptionsRequest = ListSubscriptionsRequest.builder().build();
            ListSubscriptionsResponse listSubscriptionsResponse = snsClient.listSubscriptions(listSubscriptionsRequest);
            System.out.println(listSubscriptionsResponse.subscriptions());
        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            
        }
*/
        //snsClient.close();
        return codes;
    }

    
}
