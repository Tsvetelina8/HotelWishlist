package server.api;

import commons.WishList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import server.database.HotelRepository;
import server.database.PersonRepository;
import server.database.WishListRepository;

import java.util.List;

@RestController
@RequestMapping("api/wishlist")
public class WishListController {

    PersonRepository personRepository;
    WishListRepository wishListRepository;
    HotelRepository hotelRepository;

    /**
     * Constructs a WishListController with the wish list repository.
     * @param personRepository the person repository
     * @param wishListRepository the wish list repository
     * @param hotelRepository the hotel repository
     */
    public WishListController(PersonRepository personRepository, WishListRepository wishListRepository, HotelRepository hotelRepository) {
        this.personRepository = personRepository;
        this.wishListRepository = wishListRepository;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Get mapping for /api/wishlist/person/{username} to get all wish lists for a person
     * @param username the username specified in the path
     * @return list of all wish lists for the person
     */
    @GetMapping("/person/{username}/all")
    public ResponseEntity<List<WishList>> getAllWishLists(@PathVariable String username) {
        if(!personRepository.existsById(username)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personRepository.findById(username).get().getWishLists());
    }


    /**
     * Get mapping for /api/wishlist/person/{username}/{name} to get a wish list by name
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @return the wish list with the specified name
     */
    @GetMapping("/person/{username}/{name}")
    public ResponseEntity<WishList> getWishListByName(@PathVariable String username, @PathVariable String name) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> ResponseEntity.ok(wishList))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Post mapping for /api/wishlist/person/{username} to create a wish list
     * @param wishList the wish list to be created
     * @return the created wish list
     */
    @PostMapping("/person/{username}")
    public ResponseEntity<WishList> createWishList(@PathVariable String username, @RequestBody WishList wishList) {
        if (personRepository.findById(username).get().getWishLists().stream().anyMatch(wl -> wl.getName().equals(wishList.getName()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wish list with name already exists");
        }
        wishList.setOwner(personRepository.findById(username).get());
        WishList savedWishList = wishListRepository.save(wishList);
        return ResponseEntity.ok(savedWishList);
    }

    /**
     * Put mapping for /api/wishlist/person/{username}/{name} to update a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @param updatedWishList the updated wish list
     * @return the updated wish list
     */
    @PutMapping("/person/{username}/{name}")
    public ResponseEntity<WishList> updateWishList(@PathVariable String username, @PathVariable String name, @RequestBody WishList updatedWishList) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> {
                    wishList.setName(updatedWishList.getName());
                    WishList savedWishList = wishListRepository.save(wishList);
                    return ResponseEntity.ok(savedWishList);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete mapping for /api/wishlist/person/{username}/{name} to delete a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @return the response entity
     */
    @DeleteMapping("/person/{username}/{name}")
    public ResponseEntity<Void> deleteWishList(@PathVariable String username, @PathVariable String name) {
        return personRepository.findById(username)
                .map(person -> person.getWishLists().stream()
                        .filter(wishList -> wishList.getName().equals(name))
                        .findFirst()
                        .map(wishList -> {
                            wishListRepository.delete(wishList); // Delete by the wishlist itself, no need for id.
                            return ResponseEntity.noContent().<Void>build();
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Post mapping for /api/wishlist/person/{username}/{name}/addHotel/{hotelId} to add a hotel to a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @param hotelId the hotel id specified in the path
     * @return the updated wish list
     */
    @PostMapping("/person/{username}/{name}/addHotel/{hotelId}")
    public ResponseEntity<WishList> addHotelToWishList(@PathVariable String username, @PathVariable String name, @PathVariable long hotelId) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> {
                    wishList.addHotel(hotelRepository.findById(hotelId).get());
                    WishList savedWishList = wishListRepository.save(wishList);
                    return ResponseEntity.ok(savedWishList);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete mapping for /api/wishlist/person/{username}/{name}/removeHotel/{hotelId} to remove a hotel from a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @param hotelId the hotel id specified in the path
     * @return the updated wish list
     */
    @DeleteMapping("/person/{username}/{name}/removeHotel/{hotelId}")
    public ResponseEntity<WishList> removeHotelFromWishList(@PathVariable String username, @PathVariable String name, @PathVariable long hotelId) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> {
                    wishList.removeHotel(hotelRepository.findById(hotelId).get());
                    WishList savedWishList = wishListRepository.save(wishList);
                    return ResponseEntity.ok(savedWishList);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get mapping for /api/person/{username}/wishlist/{id}/share to share a wish list
     * @param name the name specified in the path
     * @return the response entity
     */
    @GetMapping("/person/{username}/wishlist/{name}/share")
    public ResponseEntity<String> shareWishList(@PathVariable String username, @PathVariable String name) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> ResponseEntity.ok(wishList.getSharingCode()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get mapping for /api/wishlist/shared/{code} to get a wish list by sharing code
     * @param code the code specified in the path
     * @return the wish list with the specified sharing code
     */
    @GetMapping("/shared/{code}")
    public ResponseEntity<WishList> getWishListBySharingCode(@PathVariable String code) {
        return wishListRepository.findBySharingCode(code)
                .map(wishList -> ResponseEntity.ok(wishList))
                .orElse(ResponseEntity.notFound().build());
    }

}
