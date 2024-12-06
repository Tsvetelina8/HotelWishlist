package server.api;

import commons.Hotel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.HotelFacilityRepository;
import server.database.HotelRepository;

@RestController
@RequestMapping("api/hotel")
public class HotelController {

    HotelRepository hotelRepository;

    /**
     * Constructor for HotelController
     * @param hotelRepository the hotel repository
     */
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Get mapping for /api/hotel/all to get all hotels
     * @return list of all hotels
     */
    @GetMapping("/all")
    public ResponseEntity<Iterable<Hotel>> getAll() {
        return ResponseEntity.ok(hotelRepository.findAll());
    }

    /**
     * Get mapping for /api/hotel/{id} to get a hotel by id
     * @param id the id specified in the path
     * @return  the hotel with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable long id) {
        return hotelRepository.findById(id)
                .map(hotel -> ResponseEntity.ok().body(hotel))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Post mapping for /api/hotel to create a hotel
     * @param hotel the hotel to be created
     * @return the created hotel
     */
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        if (hotelRepository.existsById(hotel.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Hotel savedHotel = hotelRepository.save(hotel);
        return ResponseEntity.ok(savedHotel);
    }

    /**
     * Put mapping for /api/hotel/{id} to update a hotel
     * @param id the id specified in the path to update
     * @param updatedHotel the updated hotel
     * @return the updated hotel
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable long id, @RequestBody Hotel updatedHotel) {
        return hotelRepository.findById(id)
                .map(existingHotel -> {
                    existingHotel.setName(updatedHotel.getName());
                    existingHotel.setStars(updatedHotel.getStars());
                    existingHotel.setPage_url(updatedHotel.getPage_url());
                    existingHotel.setPhoto(updatedHotel.getPhoto());
                    existingHotel.setFacilities(updatedHotel.getFacilities());
                    Hotel savedHotel = hotelRepository.save(existingHotel);
                    return ResponseEntity.ok(savedHotel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete mapping for /api/hotel/{id} to delete a hotel
     * @param id the id specified in the path to delete
     * @return response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable long id) {
        if (!hotelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hotelRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Get mapping for /api/hotel/{id}/facilities to get the facilities of a hotel
     * @param id the id specified in the path
     * @return the hotel with the specified id
     */
    @GetMapping("/{id}/facilities")
    public ResponseEntity<Hotel> getHotelFacilities(@PathVariable long id) {
        return hotelRepository.findById(id)
                .map(hotel -> ResponseEntity.ok().body(hotel))
                .orElse(ResponseEntity.notFound().build());
    }
}
