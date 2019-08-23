package com.yn;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yn.dao")
public class SpringbootStarter {
    private final static Logger LOGGER = LoggerFactory.getLogger(SpringbootStarter.class);
    
	public static void main(String[] args) {
	    SpringApplication.run(SpringbootStarter.class, args);
        LOGGER.info("项目启动 ");
	}
}
