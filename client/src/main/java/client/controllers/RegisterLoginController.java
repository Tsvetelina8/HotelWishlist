package client.controllers;
import commons.Person;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RegisterLoginController {

    private final WebClient webClient;

    public RegisterLoginController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/person").build();
    }

    /**
     * Register method
     * @param username the username to register
     * @return true if registration is successful, false otherwise
     */
    public boolean register(String username) {
        Person person = new Person();
        person.setUsername(username);

        // Return false in case of error
        return Boolean.TRUE.equals(webClient.post()
                .bodyValue(person)
                .retrieve()
                .toEntity(String.class)
                .doOnTerminate(() -> System.out.println("Attempted registration for user: " + username))
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("User registered successfully");
                        return true;
                    } else {
                        System.out.println("Registration failed: Such user already exists");
                        return false;
                    }
                })
                .onErrorResume(error -> {
                    System.out.println("Registration failed: Such user already exists" );
                    return Mono.just(false);
                })
                .block());
    }


    /**
     * Login method
     * @param username the username to login
     * @return true if login is successful, false otherwise
     */
    public boolean login(String username) {
        // Return false if there's an error
        return Boolean.TRUE.equals(webClient.get()
                .uri("/{username}", username)
                .retrieve()
                .toEntity(Person.class)
                .doOnTerminate(() -> System.out.println("Attempted login for user: " + username))
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                        System.out.println("Login successful for user: " + username);
                        return true;
                    }
                    else {
                        System.out.println("Login failed. Not such user found.");
                        return false;
                    }
                })
                .onErrorResume(error -> {
                    System.out.println("Login failed. Not such user found.");
                    return Mono.just(false);
                })
                .block());
    }


}
