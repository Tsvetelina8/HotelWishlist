package server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Hotel;
import commons.HotelFacility;
import commons.HotelListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.HotelFacilityRepository;
import server.database.HotelRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelFacilityRepository hotelFacilityRepository;

    public void loadHotelsFromJson(String resourcePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new IOException("File not found in the classpath: " + resourcePath);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            HotelListWrapper wrapper = objectMapper.readValue(inputStream, HotelListWrapper.class);

            List<Hotel> hotels = wrapper.getHotels();
            Set<HotelFacility> facilities = new HashSet<>();
            for(Hotel hotel : hotels) {
                facilities.addAll(hotel.getFacilities());
            }
            hotelFacilityRepository.saveAll(facilities);
            hotelRepository.saveAll(hotels);

            System.out.println("Hotels loaded into the database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
