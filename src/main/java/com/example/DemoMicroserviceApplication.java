package com.example;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(MyProps.class)
@RefreshScope
public class DemoMicroserviceApplication {

	@Autowired
    private Service service;
 
    @Autowired
    private MyProps props;
    
    @Value("${myprops.alias:alias!}")
    String alias;
    
    public static void main(String[] args) {
        SpringApplication.run(DemoMicroserviceApplication.class, args);
    }
 
    @Bean
    @RefreshScope
    public Service service(){
    	return new Service(props.getName());
    }
 
    @RequestMapping("/")
    public String home() {
        return "Hello "+service.getName()+"!" + " with alias "+alias;
    }
 
}
 
@Data
@RequiredArgsConstructor
class Service{
	@NonNull
    private final String name ;
    private Service(){name="UNKNOWN";}
}
 
@Data
@ConfigurationProperties("myprops")
class MyProps{
    private  String name = "World";
}
