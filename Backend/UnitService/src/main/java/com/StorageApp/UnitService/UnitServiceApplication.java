package com.StorageApp.UnitService;

import com.StorageApp.UnitService.model.Unit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.StorageApp.UnitService.model")
public class UnitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitServiceApplication.class, args);
	}

}
