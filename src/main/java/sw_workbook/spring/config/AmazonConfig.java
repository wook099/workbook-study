//package sw_workbook.spring.config;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import jakarta.annotation.PostConstruct;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Getter
//public class AmazonConfig {
//
//    private AWSCredentials awsCredentials;
//
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @PostConstruct
//    public void init(){ this.awsCredentials = new BasicAWSCredentials(accessKey,secretKey); }
//
//    @Bean
//    public AmazonS3(){
//        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//        return  AmazonS3ClientBuilder.standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
//    }
//
//    @Bean
//    public AWSCredentials awsCredentialsProvider(){ return new AWSStaticCredentialsProvider(awsCredentials); }
//}
