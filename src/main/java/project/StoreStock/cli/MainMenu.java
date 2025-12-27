package project.StoreStock.cli;
import org.springframework.beans.factory.annotation.Autowired;
import project.StoreStock.entity.Product;
import project.StoreStock.entity.Supplier;
import project.StoreStock.service.ProductService;
import project.StoreStock.service.SupplierService;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    @Autowired
    private ProductService productService;
    private SupplierService supplierService;

    public void start() {
        boolean continueCommand = true;
        while (continueCommand) {
            int result = optionMenu();
            switch (result) {
                case 1:
                    int productResult = entityMenu(Product.class.getName().toLowerCase());
                    switch (productResult) {
                        case 1:
                            System.out.println("Enter product ID");
                            String productID = scanner.nextLine();
                            try {
                                Product product = productService.get(productID);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                List<Product> listProduct = productService.getAll();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            System.out.println("Enter Supplier name");
                            String supplierName = scanner.nextLine();
                            System.out.println("Enter Supplier phone number");
                            String supplierPhoneNo = scanner.nextLine();
                            Supplier s = new Supplier(supplierName, supplierPhoneNo);
                            System.out.println("Enter Product name");
                            String productName = scanner.nextLine();
                            System.out.println("Enter description");
                            String description = scanner.nextLine();
                            System.out.println("Enter priority");
                            int priority = scanner.nextInt();
                            Product p = new Product(productName, description, priority, s);
                            try {
                                productService.save(p);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            System.out.println("Enter Supplier name");
                            String SupplierName = scanner.nextLine();
                            System.out.println("Enter Supplier phone number");
                            String SupplierPhoneNo = scanner.nextLine();
                            Supplier supplier = new Supplier(SupplierName, SupplierPhoneNo);
                            System.out.println("Enter Product name");
                            String ProductName = scanner.nextLine();
                            System.out.println("Enter description");
                            String Description = scanner.nextLine();
                            System.out.println("Enter priority");
                            int Priority = scanner.nextInt();
                            Product product = new Product(ProductName, Description, Priority, supplier);
                            try {
                                productService.update(product);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            System.out.println("Enter Product ID");
                            String productId = scanner.nextLine();
                            try {
                                productService.delete(productId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            System.out.println("Invalid command");
                            break;
                    }
                case 2:
                    int supplierResult = entityMenu(Supplier.class.getName().toLowerCase());
                    switch (supplierResult) {
                        case 1:
                            System.out.println("Enter a supplier ID");
                            String supplierID = scanner.nextLine();
                            try {
                                Supplier s = supplierService.get(supplierID);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                List<Supplier> listSuppliers = supplierService.getAll();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            System.out.println("Enter Supplier name");
                            String supplierName = scanner.nextLine();
                            System.out.println("Enter Supplier phone number");
                            String supplierPhoneNo = scanner.nextLine();
                            Supplier s = new Supplier(supplierName, supplierPhoneNo);
                            try {
                                supplierService.save(s);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            System.out.println("Enter Supplier name");
                            String SupplierName = scanner.nextLine();
                            System.out.println("Enter Supplier phone number");
                            String SupplierPhoneNo = scanner.nextLine();
                            Supplier supplier = new Supplier(SupplierName, SupplierPhoneNo);
                            try {
                                supplierService.update(supplier);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 5:
                            System.out.println("Enter a supplier ID");
                            String supplierId = scanner.nextLine();
                            try {
                                supplierService.delete(supplierId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            System.out.println("Invalid command");
                            break;
                    }
            }
        }
    }

    private int optionMenu(){
        while(true) {
            System.out.println("Choose an option from the following:");
            System.out.println("1. Product Service");
            System.out.println("2. Supplier Service");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            String input = scanner.nextLine();
            try {
                int option = Integer.parseInt(input);
                return option;
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
    }

    private int entityMenu(String entityName){
        System.out.println("Choose an option from the following:");
        System.out.println("1. Get a " + entityName);
        System.out.println("2. Get all " + entityName);
        System.out.println("3. Save a " + entityName);
        System.out.println("4. Update a " + entityName);
        System.out.println("5. Delete a " + entityName);
        return scanner.nextInt();
    }
}
