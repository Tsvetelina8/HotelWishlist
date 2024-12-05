package client.controllers;
import commons.Person;
import org.springframework.web.reactive.function.client.WebClient;

public class RegisterLoginController {

    private final WebClient webClient;

    public RegisterLoginController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/person").build();
    }

    /**
     * Register method
     * @param username the username to register
     */
    public void register(String username) {
        Person person = new Person();
        person.setUsername(username);

        webClient.post()
                .bodyValue(person)
                .retrieve()
                .toEntity(String.class)
                .doOnTerminate(() -> System.out.println("Attempted registration for user: " + username))
                .subscribe(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("User registered successfully");
                    } else {
                        System.out.println("Registration failed");
                    }
                });
    }

    /**
     * Login method
     * @param username the username to login
     */
    public void login(String username) {
        webClient.get()
                .uri("/{username}", username)
                .retrieve()
                .toEntity(Person.class)
                .doOnTerminate(() -> System.out.println("Attempted login for user: " + username))
                .subscribe(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("Login successful");
                    } else {
                        System.out.println("Login failed");
                    }
                });
    }
}
