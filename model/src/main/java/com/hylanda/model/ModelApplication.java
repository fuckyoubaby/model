package com.hylanda.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@SpringBootApplication
//@MapperScan("com.hylanda.model.common.mapper")
public class ModelApplication {
protected  Logger Logger = LoggerFactory.getLogger(ModelApplication.class);
	@RequestMapping("/")
	@ResponseBody
	String home() {
		
		return "hello world";
	}

	public static void main(String[] args) {
		SpringApplication.run(ModelApplication.class, args);
	}
}
