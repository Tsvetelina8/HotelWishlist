package server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.services.HotelService;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private HotelService hotelService;

    @Override
    public void run(String... args) throws Exception {
        hotelService.loadHotelsFromJson("hotels.json");
    }
}
