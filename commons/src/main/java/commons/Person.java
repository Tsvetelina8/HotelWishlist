package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Person {
    @Id
    private String username;
    @OneToMany(mappedBy = "owner",  fetch = FetchType.EAGER)
    private List<WishList> wishLists;

    /**
     * Default constructor, for object mapper
     */
    public Person() {}

    /**
     * Constructs a Person object with the given first and last names
     * @param username username of the person
     */
    public Person(String username) {
        this.username = username;
        this.wishLists = new ArrayList<>();
    }

    /**
     * Adds a wishlist to the person
     * @param wishList wishlist to add
     */
    public void addWishList(WishList wishList) {
        if(!wishLists.contains(wishList)) {
            wishLists.add(wishList);
        }
    }

    /**
     * Removes a wishlist from the person
     * @param wishList wishlist to remove
     */
    public void removeWishList(WishList wishList) {
        wishLists.remove(wishList);
    }

    /**
     * Getter for the id of the person
     * @return id of the person
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username of the person
     * @param username username of the person
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the wishlists of the person
     * @return wishlists of the person
     */
    public List<WishList> getWishLists() {
        return wishLists;
    }

    /**
     * Method to test whether two objects are equal
     * @param o Object to compare 'this' to
     * @return true/false if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username);
    }

    /**
     * Method to get the hashcode of the object
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    /**
     * Method to get the string representation of the object
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return username;
    }
}
