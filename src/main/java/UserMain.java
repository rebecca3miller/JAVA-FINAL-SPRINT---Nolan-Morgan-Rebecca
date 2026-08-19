import database.DatabaseConnection;
import membership.Membership;
import membership.MembershipDAO;
import membership.MembershipService;
import merchandise.Merchandise;
import merchandise.MerchandiseDAO;
import merchandise.MerchandiseService;
import user.Admin;
import user.Authorization;
import user.Member;
import user.Trainer;
import user.User;
import user.UserService;
import workout.WorkoutClass;
import workout.WorkoutClassDAO;
import workout.WorkoutClassService;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class UserMain {

    public static void main(String[] args) {
        try {
            UserService userService = new UserService();
            Connection connection = DatabaseConnection.getConnection();
            MembershipService membershipService = new MembershipService(new MembershipDAO(connection));
            WorkoutClassService workoutClassService = new WorkoutClassService(new WorkoutClassDAO(connection));
            MerchandiseService merchandiseService = new MerchandiseService(new MerchandiseDAO(connection));
            Scanner scanner = new Scanner(System.in);

            boolean running = true;
            while (running) {
                System.out.println("\nGym Management System");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");

                int authChoice = readInt(scanner);
                switch (authChoice) {
                    case 1:
                        User loggedInUser = loginMenu(userService, scanner);
                        if (loggedInUser != null) {
                            showRoleMenu(loggedInUser, userService, membershipService, workoutClassService, merchandiseService, scanner);
                        }
                        break;
                    case 2:
                        registerMenu(userService, scanner);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Startup failed: " + e.getMessage());
        }
    }

    private static User loginMenu(UserService userService, Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            User user = userService.authenticateUser(username, password);
            System.out.println("Login successful. Welcome, " + user.getUsername() + " (" + user.getRole() + ").");
            return user;
        } catch (IOException e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    private static void registerMenu(UserService userService, Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter role (Admin, Trainer, Member): ");
            String role = scanner.nextLine().trim();

            User user;
            switch (role) {
                case "Admin":
                    user = new Admin(username, password, email, phoneNumber, address);
                    break;
                case "Trainer":
                    user = new Trainer(username, password, email, phoneNumber, address);
                    break;
                case "Member":
                    user = new Member(username, password, email, phoneNumber, address);
                    break;
                default:
                    System.out.println("Invalid role selected.");
                    return;
            }

            userService.createUser(user);
        } catch (IOException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void showRoleMenu(User currentUser,
                                    UserService userService,
                                    MembershipService membershipService,
                                    WorkoutClassService workoutClassService,
                                    MerchandiseService merchandiseService,
                                    Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n" + currentUser.getRole() + " Menu");
            List<String> actions = Authorization.getMenuActions(currentUser.getRole());
            for (int i = 0; i < actions.size(); i++) {
                System.out.println((i + 1) + ". " + Authorization.getActionLabel(actions.get(i)));
            }
            System.out.println((actions.size() + 1) + ". Logout");
            System.out.print("Select an option: ");

            int choice = readInt(scanner);
            if (choice == actions.size() + 1) {
                loggedIn = false;
                System.out.println("Logged out.");
                continue;
            }

            if (choice < 1 || choice > actions.size()) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            String selectedAction = actions.get(choice - 1);
            executeRoleAction(currentUser, selectedAction, userService, membershipService, workoutClassService, merchandiseService, scanner);
        }
    }

    private static void executeRoleAction(User currentUser,
                                         String action,
                                         UserService userService,
                                         MembershipService membershipService,
                                         WorkoutClassService workoutClassService,
                                         MerchandiseService merchandiseService,
                                         Scanner scanner) {
        try {
            switch (action) {
                case "view_users":
                    System.out.println(userService.getAllUsers(currentUser));
                    break;
                case "delete_users":
                    System.out.print("User ID to delete: ");
                    int deleteId = readInt(scanner);
                    userService.deleteUser(currentUser, deleteId);
                    break;
                case "view_revenue":
                    membershipService.displayTotalRevenue(currentUser);
                    break;
                case "add_merchandise":
                    addMerchandiseMenu(currentUser, merchandiseService, scanner);
                    break;
                case "view_inventory":
                    System.out.println("Inventory value: $" + merchandiseService.getInventoryValue(currentUser));
                    break;
                case "manage_classes":
                    workoutClassMenu(currentUser, workoutClassService, scanner);
                    break;
                case "view_own_classes":
                    System.out.println(workoutClassService.getWorkoutClassesByTrainerId(currentUser, currentUser.getId()));
                    break;
                case "purchase_membership":
                    purchaseMembershipMenu(currentUser, membershipService, scanner);
                    break;
                case "browse_merchandise":
                    merchandiseService.browseMerchandise(currentUser);
                    break;
                case "browse_classes":
                    workoutClassService.browseClasses(currentUser);
                    break;
                case "view_own_expenses":
                    membershipService.displayUserExpenses(currentUser, currentUser.getId());
                    break;
                default:
                    System.out.println("This action is not available for your role.");
            }
        } catch (IOException e) {
            System.out.println("Action failed: " + e.getMessage());
        }
    }

    private static void addMerchandiseMenu(User currentUser, MerchandiseService merchandiseService, Scanner scanner) throws IOException {
        System.out.print("Merchandise name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Type (Food/Drink/Gear): ");
        String type = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock: ");
        int stock = readInt(scanner);

        Merchandise merchandise = new Merchandise();
        merchandise.setName(name);
        merchandise.setDescription(description);
        merchandise.setItemType(type);
        merchandise.setPrice(price);
        merchandise.setStock(stock);
        merchandiseService.addMerchandise(currentUser, merchandise);
    }

    private static void purchaseMembershipMenu(User currentUser, MembershipService membershipService, Scanner scanner) throws IOException {
        System.out.print("Membership type: ");
        String membershipType = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Membership membership = new Membership();
        membership.setUserId(currentUser.getId());
        membership.setMembershipType(membershipType);
        membership.setPrice(price);
        membershipService.purchaseMembership(currentUser, membership);
    }

    private static void workoutClassMenu(User currentUser, WorkoutClassService workoutClassService, Scanner scanner) throws IOException {
        System.out.println("Workout Class Manager");
        System.out.println("1. Create class");
        System.out.println("2. Update class");
        System.out.println("3. Delete class");
        System.out.print("Select an option: ");

        int option = readInt(scanner);
        switch (option) {
            case 1:
                System.out.print("Description: ");
                String description = scanner.nextLine();
                System.out.print("Schedule: ");
                String schedule = scanner.nextLine();
                WorkoutClass newClass = new WorkoutClass(0, currentUser.getId(), description, schedule);
                workoutClassService.createWorkoutClass(currentUser, newClass);
                break;
            case 2:
                System.out.print("Class ID: ");
                int classId = readInt(scanner);
                System.out.print("New description: ");
                String newDescription = scanner.nextLine();
                System.out.print("New schedule: ");
                String newSchedule = scanner.nextLine();
                WorkoutClass updateClass = new WorkoutClass(classId, currentUser.getId(), newDescription, newSchedule);
                workoutClassService.updateWorkoutClass(currentUser, updateClass);
                break;
            case 3:
                System.out.print("Class ID to delete: ");
                int deleteClassId = readInt(scanner);
                workoutClassService.deleteWorkoutClass(currentUser, deleteClassId);
                break;
            default:
                System.out.println("Invalid workout class option.");
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}