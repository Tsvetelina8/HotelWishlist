package server.api;

import commons.Hotel;
import commons.HotelFacility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.HotelFacilityRepository;
import server.database.HotelRepository;

import java.util.List;

@RestController
@RequestMapping("api/hotelFacility")
public class HotelFacilityController {

    HotelFacilityRepository hotelFacilityRepository;
    HotelRepository hotelRepository;

    /**
     * Constructs a HotelFacilityController with the hotel facility repository and hotel repository
     * @param hotelFacilityRepository the hotel facility repository
     * @param hotelRepository the hotel repository
     */
    public HotelFacilityController(HotelFacilityRepository hotelFacilityRepository, HotelRepository hotelRepository) {
        this.hotelFacilityRepository = hotelFacilityRepository;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Get mapping for /api/hotelFacility/{name} to get a hotel facility by name
     * @param name the name specified in the path
     * @return the hotel facility with the specified name
     */
    @GetMapping("/{name}")
    public ResponseEntity<HotelFacility> getHotelFacilityByName(@PathVariable String name) {
        return hotelFacilityRepository.findById(name)
                .map(hotelFacility -> ResponseEntity.ok().body(hotelFacility))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get mapping for /api/hotelFacility/all to get all hotel facilities
     * @return list of all hotel facilities
     */
    @GetMapping("/all")
    public ResponseEntity<Iterable<HotelFacility>> getAllHotelFacilities() {
        return ResponseEntity.ok(hotelFacilityRepository.findAll());
    }

    /**
     * Get mapping for /api/hotelFacility/{name}/hotels to get all hotels with a specific facility
     * @param name the name specified in the path
     * @return list of all hotels with the specified facility
     */
    @GetMapping("/{name}/hotels")
    public ResponseEntity<List<Hotel>> getHotelsByFacility(@PathVariable String name) {
        return ResponseEntity.ok(hotelFacilityRepository.findById(name).get().getHotels());
    }


}
