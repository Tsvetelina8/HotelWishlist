package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class Main {

    /**
     * Server entry point
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}