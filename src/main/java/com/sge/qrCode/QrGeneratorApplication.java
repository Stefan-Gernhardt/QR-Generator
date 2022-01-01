package com.sge.qrCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QrGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrGeneratorApplication.class, args);
		
		System.out.println("ready for requests");
	}

}



