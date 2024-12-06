package server.api;

import commons.Person;
import commons.WishList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.HotelRepository;
import server.database.PersonRepository;
import server.database.WishListRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/person")
public class PersonController {
    PersonRepository personRepository;
    WishListRepository wishListRepository;
    HotelRepository hotelRepository;

    /**
     * Constructs a PersonController with the person repository, wish list repository, and hotel repository.
     * @param personRepository the person repository
     * @param wishListRepository the wish list repository
     * @param hotelRepository the hotel repository
     */
    public PersonController(PersonRepository personRepository, WishListRepository wishListRepository, HotelRepository hotelRepository) {
        this.personRepository = personRepository;
        this.wishListRepository = wishListRepository;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Get mapping for /api/person/{username} to get a person by username
     * @param username the username specified in the path
     * @return the person with the specified username
     */
    @GetMapping("/{username}")
    public ResponseEntity<Person> getPersonByUserName(@PathVariable String username) {
        return personRepository.findById(username)
                .map(person -> ResponseEntity.ok().body(person))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Post mapping for /api/person to create a person
     * @param person the person to be created
     * @return the created person
     */
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        if (personRepository.existsById(person.getUsername())) {
            return ResponseEntity.badRequest().build();
        }
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.ok(savedPerson);
    }

    /**
     * Put mapping for /api/person/{username} to update a person
     * @param username the username specified in the path to update
     * @param updatedPerson the updated person
     * @return the updated person
     */
    @PutMapping("/{username}")
    public ResponseEntity<Person> updatePerson(@PathVariable String username, @RequestBody Person updatedPerson) {
        return personRepository.findById(username)
                .map(existingPerson -> {
                    existingPerson.setUsername(updatedPerson.getUsername());
                    Person savedPerson = personRepository.save(existingPerson);
                    return ResponseEntity.ok(savedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete mapping for /api/person/{username} to delete a person
     * @param username the username specified in the path to delete
     * @return response entity for the deletion of the person
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deletePerson(@PathVariable String username) {
        if (!personRepository.existsById(username)) {
            return ResponseEntity.notFound().build();
        }
        personRepository.deleteById(username);
        return ResponseEntity.ok().build();
    }

    /**
     * Get mapping for /api/person/{username}/wishlist to get all wish lists for a person
     * @param username the username specified in the path
     * @return the wish lists for the person
     */
    @GetMapping("/{username}/wishlist")
    public ResponseEntity<List<WishList>> getAllWishLists(@PathVariable String username) {
        if(!personRepository.existsById(username)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personRepository.findById(username).get().getWishLists());
    }

    /**
     * Get mapping for /api/person/{username}/wishlist/{name} to get a wish list by name
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @return the wish list with the specified name
     */
    @GetMapping("/{username}/wishlist/{name}")
    public ResponseEntity<WishList> getWishListByName(@PathVariable String username, @PathVariable String name) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> ResponseEntity.ok(wishList))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Post mapping for /api/person/{username}/wishlist to create a wish list
     * @param username the username specified in the path
     * @param wishListName the name of the wish list
     * @return the created wish list
     */
    @PostMapping("/{username}/wishlist")
    public ResponseEntity<WishList> createWishList(@PathVariable String username, @RequestParam String wishListName) {
        return personRepository.findById(username)
                .map(person -> {
                    if (wishListName == null) throw new RuntimeException("Lofi");
                    WishList wishList = new WishList(wishListName);
                    wishList.setOwner(person);
                    WishList savedWishList = wishListRepository.save(wishList);
                    person.addWishList(wishList);
                    personRepository.save(person);
                    return ResponseEntity.ok(savedWishList);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}/wishlist/{name}")
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
     * Delete mapping for /api/person/{username}/{name} to delete a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @return response entity for the deletion of the wish list
     */
    @DeleteMapping("/{username}/wishlist/{name}")
    public ResponseEntity<Void> deleteWishList(@PathVariable String username, @PathVariable String name) {
        Optional<Person> personOptional = personRepository.findById(username);

        if (personOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Person person = personOptional.get();
        System.out.println(person.getWishLists());
        WishList wishList = person.getWishLists().stream()
                .filter(wishList1 -> wishList1.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(wishList != null) {
            person.removeWishList(wishList);
            personRepository.save(person);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Post mapping for /api/person/{username}/wishlist/{name}/addHotel/{hotelId} to add a hotel to a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @param hotelId the hotel id specified in the path
     * @return the updated wish list
     */
    @PostMapping("/{username}/wishlist/{name}/addHotel/{hotelId}")
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
     * Delete mapping for /api/person/{username}/{name}/removeHotel/{hotelId} to remove a hotel from a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @param hotelId the hotel id specified in the path
     * @return the updated wish list
     */
    @DeleteMapping("/{username}/wishlist/{name}/removeHotel/{hotelId}")
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
     * Get mapping for /api/person/{username}/wishlist/{name}/share to share a wish list
     * @param username the username specified in the path
     * @param name the name specified in the path
     * @return the response entity
     */
    @GetMapping("/{username}/wishlist/{name}/share")
    public ResponseEntity<String> shareWishList(@PathVariable String username, @PathVariable String name) {
        return personRepository.findById(username).get().getWishLists().stream()
                .filter(wishList -> wishList.getName().equals(name))
                .findFirst()
                .map(wishList -> ResponseEntity.ok(wishList.getSharingCode()))
                .orElse(ResponseEntity.notFound().build());
    }


}
