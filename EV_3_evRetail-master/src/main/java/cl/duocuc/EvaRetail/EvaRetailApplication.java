package cl.duocuc.EvaRetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EvaRetailApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvaRetailApplication.class, args);
	}
}
