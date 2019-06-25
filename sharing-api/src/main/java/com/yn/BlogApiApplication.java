package com.yn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApiApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlogApiApplication.class);
    
	public static void main(String[] args) {
	    SpringApplication.run(BlogApiApplication.class, args);
        LOGGER.info("项目启动 ");
	}
}
