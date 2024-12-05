package commons;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class WishList {
    @Id
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_username")
    private Person owner;
    @ManyToMany
    @JoinTable(
            name = "wishlist_hotel",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    private List<Hotel> hotels;
    private String sharingCode;

    /**
     * Default constructor, for object mapper
     */
    public WishList() {
    }

    /**
     * Constructs a WishList object with the given parameters
     * @param name name of the wishlist
     * @param owner owner of the wishlist
     */
    public WishList(String name, Person owner) {
        this.name = name;
        this.owner = owner;
        this.hotels = new ArrayList<>();
        setSharingCode();
    }

    /**
     * Constructs a WishList object with the given parameters
     * @param wishListName name of the wishlist
     */
    public WishList(String wishListName) {
        this.name = wishListName;
        this.hotels = new ArrayList<>();
        setSharingCode();
    }

    /**
     * Adds a hotel to the wishlist
     * @param hotel hotel to add
     */
    public void addHotel(Hotel hotel) {
        if(!hotels.contains(hotel)) {
            hotels.add(hotel);
        }
    }

    /**
     * Removes a hotel from the wishlist
     * @param hotel hotel to remove
     */
    public void removeHotel(Hotel hotel) {
        hotels.remove(hotel);
    }



    /**
     * Getter for the id of the wishlist
     * @return id of the wishlist
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for the name of the wishlist
     * @return name of the wishlist
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the wishlist
     * @param name name of the wishlist
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the sharing code of the wishlist
     * @return sharing code of the wishlist
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Setter for the owner of the wishlist
     * @param owner owner of the wishlist
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    /**
     * Getter for the sharing code of the wishlist
     * @return sharing code of the wishlist
     */
    public List<Hotel> getHotels() {
        return hotels;
    }

    /**
     * Setter for the hotels of the wishlist
     * @param hotels hotels of the wishlist
     */
    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    /**
     * Setter for the sharing code of the wishlist
     * @return sharing code of the wishlist
     */
    public String getSharingCode() {
        return sharingCode;
    }

    /**
     * Setter for the sharing code of the wishlist
     */
    public void setSharingCode() {
        this.sharingCode = createSharingCode();
    }

    /**
     * Method to create a sharing code for the wishlist
     * @return sharing code
     */
    private String createSharingCode() {
        return UUID.randomUUID().toString();
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
        WishList wishList = (WishList) o;
        return id == wishList.id;
    }

    /**
     * Method to get the hashcode of the object
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Method to get the string representation of the object
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "WishList{" +
                "owner=" + owner +
                ", name='" + name + '\'' +
                ", hotels=" + hotels +
                '}';
    }

}
