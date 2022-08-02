package com.exerciseday.dev.utils;

import org.springframework.stereotype.Service;

//import com.exerciseday.dev.config.AWSconfig;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

//SNS서비스 접근 권한
/*
@Service
public class SmsService {
    AWSconfig awsConfig;

    public SmsService(AWSconfig awsConfig){
        this.awsConfig = awsConfig;
    }

    public AwsCredentialsProvider geAwsCredentials (String accessKeyID, String secretAccessKey){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyID, secretAccessKey);
        return () -> awsBasicCredentials;
    }

    public SnsClient getSnsClient(){
        return SnsClient.builder()
        .credentialsProvider(
            geAwsCredentials(awsConfig.getAwsAccessKey(), awsConfig.getAwsSecretKey())
            ).region(Region.of(awsConfig.getAwsRegion())
            ).build();
        
    }
}
*/