package client.controllers;

import org.springframework.web.reactive.function.client.WebClient;
import commons.Hotel;

public class HotelController {

    private final WebClient webClient;
    private final String apiUrl = "http://localhost:8080/api/hotel";  // Change this to your backend API URL

    public HotelController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    /**
     * Get all hotels
     */
    public void getAllHotels() {
        webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(Hotel.class)
                .doOnTerminate(() -> System.out.println("Fetching all hotels completed."))
                .subscribe(hotel -> System.out.println("Hotel: " + hotel.getName() + ", ID: " + hotel.getId()));
    }

    /**
     * Get hotel by ID
     * @param id the ID of the hotel
     */
    public void getHotelById(Long id) {
        webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Hotel.class)
                .doOnTerminate(() -> System.out.println("Fetching hotel by ID completed."))
                .subscribe(hotel -> System.out.println("Hotel Details: " + hotel));
    }
}
