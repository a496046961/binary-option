package org.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
		System.out.println("启动成功");
	}

}
