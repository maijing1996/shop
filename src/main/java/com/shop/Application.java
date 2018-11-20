package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.shop.conf.MySiteSetting;

@Controller
@EnableWebMvc
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.shop")
@EnableConfigurationProperties({ MySiteSetting.class })
public class Application {
    public static void main( String[] args ) {
    	SpringApplication.run(Application.class, args);
    }
}
