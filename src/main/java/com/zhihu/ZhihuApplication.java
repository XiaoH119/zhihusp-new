package com.zhihu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ZhihuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhihuApplication.class, args);
	}

}
