package project.StoreStock.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.StoreStock.entity.User;
import project.StoreStock.service.UserService;

import java.util.List;
import java.util.Scanner;

@Component
public class UserMenu {

    private static final Scanner scanner = new Scanner(System.in);

    @Autowired
    private UserService userService;

    public void start() {
        boolean continueCommand = true;

        while (continueCommand) {
            int result = optionMenu();

            switch (result) {
                case 1:
                    handleGetUser();
                    break;
                case 2:
                    handleGetAllUsers();
                    break;
                case 3:
                    handleCreateUser();
                    break;
                case 4:
                    handleUpdateUser();
                    break;
                case 5:
                    handleDeleteUser();
                    break;
                case 6:
                    continueCommand = false;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private int optionMenu() {
        while (true) {
            System.out.println("\n=== USER MANAGEMENT ===");
            System.out.println("1. Get a User");
            System.out.println("2. Get all Users");
            System.out.println("3. Create a User");
            System.out.println("4. Update a User");
            System.out.println("5. Delete a User");
            System.out.println("6. Back");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 6) return choice;
                System.out.println("Please enter a number between 1 and 6");
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private void handleGetUser() {
        System.out.print("Enter User ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            User user = userService.get(id);
            System.out.println("User found: " + user);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleGetAllUsers() {
        try {
            List<User> users = userService.getAll();
            System.out.println("\n=== All Users ===");
            users.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateUser() {
        System.out.println("\n=== Create User ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Does user have permission? (true/false): ");
        Boolean hasPermission = Boolean.parseBoolean(scanner.nextLine());

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setHasPermission(hasPermission);

        try {
            userService.save(user);
            System.out.println("User created successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdateUser() {
        System.out.println("\n=== Update User ===");

        System.out.print("Enter User ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new username: ");
            String username = scanner.nextLine();

            System.out.print("Enter new password: ");
            String password = scanner.nextLine();

            System.out.print("Does user have permission? (true/false): ");
            Boolean hasPermission = Boolean.parseBoolean(scanner.nextLine());

            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setHasPermission(hasPermission);

            userService.update(user);
            System.out.println("User updated successfully!");

        } catch (Exception e) {
            System.out.println("Invalid input or error: " + e.getMessage());
        }
    }

    private void handleDeleteUser() {
        System.out.print("Enter User ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            userService.delete(id);
            System.out.println("User deleted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}