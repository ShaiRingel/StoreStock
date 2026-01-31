package project.StoreStock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import project.StoreStock.cli.UserMenu;

@SpringBootApplication
public class StoreStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreStockApplication.class, args);

	}

	/*
	@Bean
	CommandLineRunner run(UserMenu mainMenu) {
		return args -> mainMenu.start();
	}
	 */

}
