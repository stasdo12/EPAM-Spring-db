package com.epam.springcore.task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = "com.epam.springcore")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.epam.springcore.task.client")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}