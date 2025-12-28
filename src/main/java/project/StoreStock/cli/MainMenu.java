package project.StoreStock.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;
import project.StoreStock.service.ProductService;
import project.StoreStock.service.SupplierService;

import java.util.List;
import java.util.Scanner;

@Component
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
                System.out.print("Enter product ID: ");
                int productID = scanner.nextInt();
                scanner.nextLine();
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
                createOrUpdateProduct(true);
                break;
            case 4:
                createOrUpdateProduct(false);
                break;
            case 5:
                System.out.print("Enter Product ID: ");
                int productId = scanner.nextInt();
                scanner.nextLine();
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

    private void createOrUpdateProduct(boolean isCreate) {
        scanner.nextLine();

        System.out.println("\n=== Supplier Information ===");
        System.out.println("Do you want to:");
        System.out.println("1. Create a new supplier");
        System.out.println("2. Use an existing supplier by ID");
        System.out.print("Enter your choice: ");

        int supplierChoice = 0;
        try {
            supplierChoice = Integer.parseInt(scanner.nextLine());
            if (supplierChoice < 1 || supplierChoice > 2) {
                System.out.println("Invalid choice. Using option 1.");
                supplierChoice = 1;
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Using option 1.");
            supplierChoice = 1;
        }

        Supplier supplier = null;
        boolean createNewSupplier = (supplierChoice == 1);

        if (createNewSupplier) {
            System.out.print("Enter Supplier name: ");
            String supplierName = scanner.nextLine();
            System.out.print("Enter Supplier phone number: ");
            String supplierPhoneNo = scanner.nextLine();
            supplier = new Supplier(supplierName, supplierPhoneNo);
        } else {
            System.out.print("Enter existing Supplier ID: ");
            try {
                int supplierId = Integer.parseInt(scanner.nextLine());
                try {
                    supplier = supplierService.get(supplierId);
                    if (supplier == null) {
                        System.out.println("Supplier not found. Creating new supplier instead...");
                        System.out.print("Enter Supplier name: ");
                        String supplierName = scanner.nextLine();
                        System.out.print("Enter Supplier phone number: ");
                        String supplierPhoneNo = scanner.nextLine();
                        supplier = new Supplier(supplierName, supplierPhoneNo);
                        createNewSupplier = true;
                    }
                    else{
                        System.out.println("\n=== Which Field To Update? ===");
                        System.out.println("1. Supplier Name");
                        System.out.println("2. Supplier Phone Number");
                        System.out.print("Enter your choice: ");
                        int updateField = 1;
                        try{
                            updateField = Integer.parseInt(scanner.nextLine());
                            if(updateField < 1 || updateField > 2){
                                System.out.println("Invalid input. Updating by Supplier Name.");
                                updateField = 1;
                            }
                        }catch(Exception e){
                            System.out.println("Invalid input. Updating by Supplier Name.");
                            updateField = 1;
                        }
                        boolean updateByName = (updateField == 1);
                        if(updateByName){
                            System.out.print("Enter Supplier name: ");
                            String supplierName = scanner.nextLine();
                            supplier =  supplierService.get(supplierId);
                            supplier.setName(supplierName);
                        }else {
                            System.out.print("Enter Supplier Phone Number: ");
                            String supplierPhoneNo = scanner.nextLine();
                            supplier =  supplierService.get(supplierId);
                            supplier.setPhone(supplierPhoneNo);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid supplier ID.");
                return;
            }
        }

        System.out.println("\n=== Product Information ===");
        System.out.print("Enter Product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter priority (1-5): ");
        int priority = 0;
        try {
            priority = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid priority. Using default: 3");
            priority = 3;
        }

        Product product = new Product(productName, description, priority, supplier);

        if (isCreate) {
            try {
                productService.saveWithSupplier(product, createNewSupplier);
                System.out.println("Product saved successfully!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.print("Enter Product ID to update: ");
            try {
                int productId = Integer.parseInt(scanner.nextLine());
                product.setId(productId);
                try {
                    productService.updateWithSupplier(product, createNewSupplier);
                    System.out.println("Product updated successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Invalid product ID.");
            }
        }
    }

    private void handleSupplierOperations(int operation) {
        switch (operation) {
            case 1:
                System.out.print("Enter supplier ID: ");
                int supplierID = scanner.nextInt();
                scanner.nextLine();
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
                createOrUpdateSupplier(true);
                break;
            case 4:
                createOrUpdateSupplier(false);
                break;
            case 5:
                System.out.print("Enter supplier ID: ");
                int supplierId = scanner.nextInt();
                scanner.nextLine();
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

    private void createOrUpdateSupplier(boolean isCreate) {
        scanner.nextLine();

        System.out.print("Enter Supplier name: ");
        String supplierName = scanner.nextLine();
        System.out.print("Enter Supplier phone number: ");
        String supplierPhoneNo = scanner.nextLine();

        if (isCreate) {
            Supplier s = new Supplier(supplierName, supplierPhoneNo);
            try {
                supplierService.save(s);
                System.out.println("Supplier saved successfully!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.print("Enter Supplier ID to update: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                Supplier s = new Supplier(id, supplierName, supplierPhoneNo);
                try {
                    supplierService.update(s);
                    System.out.println("Supplier updated successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Invalid ID.");
            }
        }
    }

    private int optionMenu() {
        while(true) {
            System.out.println("\n=== Store Stock Management ===");
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
                System.out.println("Please enter 1, 2, or 3");
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private int entityMenu(String entityName) {
        System.out.println("\n=== " + entityName.toUpperCase() + " MENU ===");
        System.out.println("1. Get a " + entityName);
        System.out.println("2. Get all " + entityName + "s");
        System.out.println("3. Save a " + entityName);
        System.out.println("4. Update a " + entityName);
        System.out.println("5. Delete a " + entityName);
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return 0; // Invalid
        }
    }
}