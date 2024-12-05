package client.controllers;

import commons.WishList;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class WishListController {

    private final WebClient webClient;

    public WishListController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/wishlist/").build(); // Backend base URL
    }

    /**
     * Get all wishlists for a user
     * @param username the username to get wishlists for
     */
    public void getAllWishLists(String username) {
        webClient.get()
                .uri("/person/{username}/all", username)
                .retrieve()
                .bodyToMono(List.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("All WishLists: " + response);
                });
    }

    /**
     * Get a wishlist by name
     * @param username the username to get the wishlist for
     * @param name the name of the wishlist
     */
    public void getWishListByName(String username, String name) {
        webClient.get()
                .uri("/person/{username}/wishlist/{name}", username, name)
                .retrieve()
                .bodyToMono(WishList.class)
                .subscribe(response -> {
                    System.out.println("WishList with Name " + name + ": " + response);
                });
    }

    /**
     * Create a new wishlist
     * @param username the username to create the wishlist for
     * @param wishListName the name of the wishlist
     */
    public void createWishList(String username, String wishListName) {
        webClient.post()
                .uri("/person/{username}", username)
                .bodyValue(new WishList(wishListName)) // Assuming WishList is a class that holds wish list details
                .retrieve()
                .bodyToMono(WishList.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("Created WishList: " + response);
                });
    }

    /**
     * Remove a wishlist by name
     * @param username the username to remove the wishlist for
     * @param name the name of the wishlist to remove
     */
    public void removeWishList(String username, String name) {
        webClient.delete()
                .uri("/person/{username}/wishlist/{name}", username, name)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("WishList with Name " + name + " removed.");
                });
    }

    /**
     * Add a hotel to a wishlist by name
     * @param username the username to add the hotel to
     * @param name the name of the wishlist to add the hotel to
     * @param hotelId the id of the hotel to add
     */
    public void addHotelToWishList(String username, String name, long hotelId) {
        webClient.post()
                .uri("/person/{username}/wishlist/{name}/addHotel/{hotelId}", username, name, hotelId)
                .retrieve()
                .bodyToMono(WishList.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("Hotel added to WishList: " + response);
                });
    }

    /**
     * Remove a hotel from a wishlist by name
     * @param username the username to remove the hotel from
     * @param name the name of the wishlist to remove the hotel from
     * @param hotelId the id of the hotel to remove
     */
    public void removeHotelFromWishList(String username, String name, long hotelId) {
        webClient.delete()
                .uri("/person/{username}/wishlist/{name}/removeHotel/{hotelId}", username, name, hotelId)
                .retrieve()
                .bodyToMono(WishList.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("Hotel removed from WishList: " + response);
                });
    }

    /**
     * Share a wishlist by name
     * @param username the username to share the wishlist for
     * @param name the name of the wishlist to share
     */
    public void shareWishList(String username, String name) {
        webClient.get()
                .uri("/person/{username}/wishlist/{name}/share", username, name)
                .retrieve()
                .bodyToMono(String.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("WishList shared with code: " + response);
                });
    }

    /**
     * Get a wishlist by sharing code
     * @param sharingCode the sharing code of the wishlist
     */
    public void getWishListBySharingCode(String sharingCode) {
        webClient.get()
                .uri("/shared/{sharingCode}", sharingCode)
                .retrieve()
                .bodyToMono(WishList.class)
                .doOnTerminate(() -> System.out.println("Request Completed"))
                .subscribe(response -> {
                    System.out.println("WishList with Sharing Code " + sharingCode + ": " + response);
                });
    }
}
