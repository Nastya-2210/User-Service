import models.User;
import services.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        try {
            System.out.println("=== User Service Application ===");
            showMenu();
        } finally {
            scanner.close();
            utils.HibernateSessionFactoryUtil.shutdown();
        }
    }

    private static void showMenu() {
        while (true) {
            printMenuOptions();
            System.out.print("Choose an option (1-6): ");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Please enter a number!");
                continue;
            }

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> createUser();
                    case 2 -> findUserById();
                    case 3 -> findAllUsers();
                    case 4 -> updateUser();
                    case 5 -> deleteUser();
                    case 6 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option! Please choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-6)!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace(); // Для отладки
            }

            System.out.println("\n" + "=".repeat(50));
        }
    }

    private static void printMenuOptions() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Create new user");
        System.out.println("2. Find user by ID");
        System.out.println("3. Show all users");
        System.out.println("4. Update user");
        System.out.println("5. Delete user");
        System.out.println("6. Exit");
    }

    private static void createUser() {
        System.out.println("\n--- Create New User ---");

        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }

            System.out.print("Enter age: ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.isEmpty()) {
                System.out.println("Age cannot be empty!");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageInput);
            } catch (NumberFormatException e) {
                System.out.println("Age must be a valid number!");
                return;
            }

            if (age <= 0 || age > 150) {
                System.out.println("Age must be between 1 and 150!");
                return;
            }

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty!");
                return;
            }
            if (!email.contains("@")) {
                System.out.println("Invalid email format! Must contain '@'");
                return;
            }

            User user = new User(name, age, email);
            userService.saveUser(user);
            System.out.println("User created successfully! ID: " + user.getId());

        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private static void findUserById() {
        System.out.println("\n--- Find User by ID ---");

        try {
            System.out.print("Enter user ID: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                System.out.println("ID cannot be empty!");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a valid number!");
                return;
            }

            if (id <= 0) {
                System.out.println("ID must be positive!");
                return;
            }

            User user = userService.findUser(id);
            if (user != null) {
                System.out.println("User found:");
                printUserDetails(user);
            } else {
                System.out.println("User with ID " + id + " not found!");
            }

        } catch (Exception e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
    }

    private static void findAllUsers() {
        System.out.println("\n--- All Users ---");

        try {
            List<User> users = userService.findAllUsers();

            if (users.isEmpty()) {
                System.out.println("No users found in database.");
            } else {
                System.out.println("Total users: " + users.size());
                for (int i = 0; i < users.size(); i++) {
                    System.out.println("\nUser #" + (i + 1) + ":");
                    printUserDetails(users.get(i));
                }
            }

        } catch (Exception e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }

    private static void updateUser() {
        System.out.println("\n--- Update User ---");

        try {
            System.out.print("Enter user ID to update: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                System.out.println("ID cannot be empty!");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a valid number!");
                return;
            }

            User user = userService.findUser(id);
            if (user == null) {
                System.out.println("User with ID " + id + " not found!");
                return;
            }

            System.out.println("Current user details:");
            printUserDetails(user);
            System.out.println("\nEnter new details (press Enter to keep current value):");

            // Обновление имени
            System.out.print("Name [" + user.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                user.setName(name);
            }

            // Обновление возраста
            System.out.print("Age [" + user.getAge() + "]: ");
            String ageInput = scanner.nextLine().trim();
            if (!ageInput.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageInput);
                    if (age <= 0 || age > 150) {
                        System.out.println("Age must be between 1 and 150! Age not updated.");
                    } else {
                        user.setAge(age);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format! Age not updated.");
                }
            }

            // Обновление email
            System.out.print("Email [" + user.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) {
                if (!email.contains("@")) {
                    System.out.println("Invalid email format! Email not updated.");
                } else {
                    user.setEmail(email);
                }
            }

            userService.updateUser(user);
            System.out.println("User updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        System.out.println("\n--- Delete User ---");

        try {
            System.out.print("Enter user ID to delete: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                System.out.println("ID cannot be empty!");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a valid number!");
                return;
            }

            User user = userService.findUser(id);
            if (user == null) {
                System.out.println("User with ID " + id + " not found!");
                return;
            }

            System.out.println("User to delete:");
            printUserDetails(user);

            System.out.print("Are you sure you want to delete this user? (yes/NO): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes") || confirmation.equals("y")) {
                userService.deleteUser(user);
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    private static void printUserDetails(User user) {
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Created: " + user.getCreatedAt());
    }
}