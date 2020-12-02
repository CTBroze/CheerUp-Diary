package kr.ac.mju.teamcheerup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CheerupdiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheerupdiaryApplication.class, args);
	}

}
