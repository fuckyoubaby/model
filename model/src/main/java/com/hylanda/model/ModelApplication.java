package com.hylanda.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Controller
@SpringBootApplication
@EnableTransactionManagement
//@MapperScan("com.hylanda.model.common.mapper")
public class ModelApplication {
protected  Logger Logger = LoggerFactory.getLogger(ModelApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ModelApplication.class, args);
	}
}
