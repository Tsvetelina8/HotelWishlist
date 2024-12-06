package client;

import client.controllers.HotelController;
import client.controllers.RegisterLoginController;
import client.controllers.WishListController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Scanner;

public class Main {

    private static boolean isAuthenticated = false;

    public static void main(String[] args) {
        WebClient.Builder webClientBuilder = WebClient.builder();

        // Initialize controllers
        RegisterLoginController registerLoginController = new RegisterLoginController(webClientBuilder);
        HotelController hotelController = new HotelController(webClientBuilder);
        WishListController wishListController = new WishListController(webClientBuilder);

        Scanner scanner = new Scanner(System.in);
        String username = "";


        while (!isAuthenticated) {

            System.out.println("Welcome!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            String choice = null;
            while (choice == null || choice.trim().isEmpty()) {

                if(scanner.hasNextLine()) {
                    choice = scanner.nextLine().trim();
                }

            }

            switch (choice) {
                case "1":
                    // Login process
                    System.out.print("Enter username to login: ");
                    username = scanner.nextLine().trim();
                    if (username.isEmpty()) {
                        System.out.println("Username cannot be empty. Please try again.");
                    } else {
                        if(registerLoginController.login(username)){
                            isAuthenticated = true;
                        }
                    }
                    break;

                case "2":
                    // Registration process
                    System.out.print("Enter username to register: ");
                    username = scanner.nextLine().trim();
                    if (username.isEmpty()) {
                        System.out.println("Username cannot be empty. Please try again.");
                    } else {
                        if(registerLoginController.register(username)){
                            isAuthenticated = true;
                        }
                    }
                    break;

                case "3":
                    // Exit the application
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }


                boolean exit = false;
                while (!exit && isAuthenticated) {

                    System.out.println("\nChoose an option:");
                    System.out.println("1. View all hotels");
                    System.out.println("2. View a specific hotel");
                    System.out.println("3. View all wishlists");
                    System.out.println("4. View a wishlist by name");
                    System.out.println("5. Create a new wishlist");
                    System.out.println("6. Remove a wishlist");
                    System.out.println("7. Add a hotel to a wishlist");
                    System.out.println("8. Remove a hotel from a wishlist");
                    System.out.println("9. Share a wishlist");
                    System.out.println("10. View a shared wishlist");
                    System.out.println("11. Logout");
                    System.out.println("12. Exit");

                    String option = null;
                    while (option == null || option.trim().isEmpty()) {

                        option = scanner.nextLine().trim();
                        if (option.isEmpty()) {
                            System.out.println("Please enter a valid option (1-12):");
                        }
                    }

                    switch (option) {
                        case "1":
                            // View all hotels
                            hotelController.getAllHotels();
                            break;

                        case "2":
                            // View a specific hotel
                            System.out.print("Enter hotel ID to view: ");
                            try {
                                long hotelIdToView = Long.parseLong(scanner.nextLine().trim());
                                hotelController.getHotelById(hotelIdToView);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid hotel ID. Please enter a valid number.");
                            }
                            break;

                        case "3":
                            // View all wishlists for the authenticated user
                            wishListController.getAllWishLists(username);
                            break;

                        case "4":
                            // View a specific wishlist by name
                            System.out.print("Enter wishlist name: ");
                            String name = scanner.nextLine().trim();
                            wishListController.getWishListByName(username, name);
                            break;

                        case "5":
                            // Create a new wishlist
                            System.out.print("Enter new wishlist name: ");
                            String newWishListName = scanner.nextLine().trim();
                            wishListController.createWishList(username, newWishListName);
                            break;

                        case "6":
                            // Remove a wishlist by name
                            System.out.print("Enter the name of the wishlist to remove: ");
                            String removeName = scanner.nextLine().trim();
                            wishListController.removeWishList(username, removeName);
                            break;

                        case "7":
                            // Add a hotel to a wishlist
                            System.out.print("Enter the wishlist name to add hotel to: ");
                            String addWishListName = scanner.nextLine().trim();
                            System.out.print("Enter hotel ID: ");
                            try {
                                long hotelId = Long.parseLong(scanner.nextLine().trim());
                                wishListController.addHotelToWishList(username, addWishListName, hotelId);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid hotel ID. Please enter a valid number.");
                            }
                            break;

                        case "8":
                            // Remove a hotel from a wishlist
                            System.out.print("Enter the wishlist name to remove hotel from: ");
                            String removeWishListName = scanner.nextLine().trim();
                            System.out.print("Enter hotel ID: ");
                            try {
                                long removeHotelId = Long.parseLong(scanner.nextLine().trim());
                                wishListController.removeHotelFromWishList(username, removeWishListName, removeHotelId);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid hotel ID. Please enter a valid number.");
                            }
                            break;

                        case "9":
                            // Share a wishlist
                            System.out.print("Enter the wishlist name to share: ");
                            String shareWishListName = scanner.nextLine().trim();
                            wishListController.shareWishList(username, shareWishListName);
                            break;

                        case "10":
                            // View a shared wishlist
                            System.out.print("Enter the sharing code to view the wishlist: ");
                            String sharingCode = scanner.nextLine().trim();
                            wishListController.getWishListBySharingCode(sharingCode);
                            break;

                        case "11":
                            // Logout
                            System.out.println("You have logged out.");
                            username = "";
                            isAuthenticated = false;
                            break;

                        case "12":
                            // Exit the application
                            exit = true;
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }


        }



        scanner.close();
    }
}

