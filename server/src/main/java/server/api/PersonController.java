package server.api;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.PersonRepository;
import server.database.WishListRepository;


@RestController
@RequestMapping("api/person")
public class PersonController {
    private final PersonRepository personRepository;
    WishListRepository wishListRepository;
    PersonController personController;

    /**
     * Constructs a PersonController with the person repository.
     * @param wishListRepository the wish list repository
     * @param personRepository the person repository
     */
    public PersonController(WishListRepository wishListRepository, PersonRepository personRepository) {
        this.wishListRepository = wishListRepository;
        this.personRepository = personRepository;
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


}
