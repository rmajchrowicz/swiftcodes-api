package com.example.swiftcodes;

import com.example.swiftcodes.service.SwiftCodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SwiftCodeApplication implements CommandLineRunner {

	@Autowired
	private SwiftCodeParser swiftCodeParser;

	public static void main(String[] args) {
		SpringApplication.run(SwiftCodeApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		swiftCodeParser.parseExcelFile("src/main/resources/Interns_2025_SWIFT_CODES.xlsx");
	}
}