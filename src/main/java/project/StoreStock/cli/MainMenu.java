package project.StoreStock.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;
import project.StoreStock.service.ProductService;
import project.StoreStock.service.SupplierService;

import java.util.List;
import java.util.Scanner;

@Component  // Added @Component annotation
public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    public void start() {
        boolean continueCommand = true;
        while (continueCommand) {
            int result = optionMenu();
            switch (result) {
                case 1:
                    int productResult = entityMenu("product");
                    handleProductOperations(productResult);
                    break;
                case 2:
                    int supplierResult = entityMenu("supplier");
                    handleSupplierOperations(supplierResult);
                    break;
                case 3:
                    continueCommand = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
        scanner.close();
    }

    private void handleProductOperations(int operation) {
        switch (operation) {
            case 1:
                System.out.println("Enter product ID");
                int productID = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                try {
                    Product product = productService.get(productID);
                    System.out.println("Product found: " + product);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    List<Product> listProduct = productService.getAll();
                    System.out.println("All Products:");
                    listProduct.forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 3:
                productChanges(3);
                break;
            case 4:
                productChanges(4);
                break;
            case 5:
                System.out.println("Enter Product ID");
                int productId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                try {
                    productService.delete(productId);
                    System.out.println("Product deleted successfully");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    private void handleSupplierOperations(int operation) {
        switch (operation) {
            case 1:
                System.out.println("Enter a supplier ID");
                int supplierID = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                try {
                    Supplier s = supplierService.get(supplierID);
                    System.out.println("Supplier found: " + s);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    List<Supplier> listSuppliers = supplierService.getAll();
                    System.out.println("All Suppliers:");
                    listSuppliers.forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 3:
                supplierChanges(3);
                break;
            case 4:
                supplierChanges(4);
                break;
            case 5:
                System.out.println("Enter a supplier ID");
                int supplierId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                try {
                    supplierService.delete(supplierId);
                    System.out.println("Supplier deleted successfully");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    private int optionMenu() {
        while(true) {
            System.out.println("\n=== Store Stock Management ===");
            System.out.println("Choose an option from the following:");
            System.out.println("1. Product Service");
            System.out.println("2. Supplier Service");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
                System.out.println("Please enter a number between 1 and 3");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private int entityMenu(String entityName) {
        while(true) {
            System.out.println("\n=== " + entityName.toUpperCase() + " MENU ===");
            System.out.println("Choose an option from the following:");
            System.out.println("1. Get a " + entityName);
            System.out.println("2. Get all " + entityName + "s");
            System.out.println("3. Save a " + entityName);
            System.out.println("4. Update a " + entityName);
            System.out.println("5. Delete a " + entityName);
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 5) {
                    return choice;
                }
                System.out.println("Please enter a number between 1 and 5");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void productChanges(int commandNumber) {
        System.out.print("Enter Supplier name: ");
        String supplierName = scanner.nextLine();
        System.out.print("Enter Supplier phone number: ");
        String supplierPhoneNo = scanner.nextLine();
        Supplier s = new Supplier(supplierName, supplierPhoneNo);

        System.out.print("Enter Product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter priority (1-5): ");
        int priority = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product p = new Product(productName, description, priority, s);

        if(commandNumber == 3){
            try {
                productService.save(p);
                System.out.println("Product saved successfully!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        else {
            System.out.print("Enter Product ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            p.setId(id);
            try {
                productService.update(p);
                System.out.println("Product updated successfully!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void supplierChanges(int commandNumber) {
        System.out.print("Enter Supplier name: ");
        String supplierName = scanner.nextLine();
        System.out.print("Enter Supplier phone number: ");
        String supplierPhoneNo = scanner.nextLine();

        if(commandNumber == 3){
            Supplier s = new Supplier(supplierName, supplierPhoneNo);
            try {
                supplierService.save(s);
                System.out.println("Supplier saved successfully!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        else {
            System.out.print("Enter Supplier ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Supplier s = new Supplier(supplierName, supplierPhoneNo);
            s.setId(id);
            try {
                supplierService.update(s);
                System.out.println("Supplier updated successfully!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}