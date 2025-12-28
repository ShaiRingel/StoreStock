package project.StoreStock;

import com.sun.tools.javac.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import project.StoreStock.cli.MainMenu;
import project.StoreStock.dal.ProductDAO;
import project.StoreStock.dal.ProductFileDao;
import project.StoreStock.dal.SupplierDAO;
import project.StoreStock.dal.SupplierFileDao;
import project.StoreStock.service.ProductService;
import project.StoreStock.service.SupplierService;

@SpringBootApplication
public class StoreStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreStockApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(MainMenu mainMenu) {
		return args -> {
			mainMenu.start();
		};
	}

}
