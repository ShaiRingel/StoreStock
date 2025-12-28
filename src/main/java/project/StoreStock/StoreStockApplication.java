package project.StoreStock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import project.StoreStock.cli.MainMenu;

@SpringBootApplication
public class StoreStockApplication {

	public static void main(String[] args) {
		SpringApplication.exit(SpringApplication.run(StoreStockApplication.class, args));
	}

	@Bean
	public CommandLineRunner run(MainMenu mainMenu) {
		return args -> {
			mainMenu.start();
		};
	}

}
