package commons;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int stars;
    private String page_url;
    private String photo;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_to_facility",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_name")
    )
    private List<HotelFacility> facilities;

    /**
     * Default constructor, for object mapper
     */
    public Hotel() {}

    /**
     * Constructs a Hotel object with the given parameters
     * @param name name of the hotel
     * @param stars number of stars of the hotel
     * @param page_url url of the hotel
     * @param photo photo of the hotel
     * @param facilities list of facilities of the hotel
     */
    public Hotel(String name, int stars, String page_url, String photo, List<HotelFacility> facilities) {
        this.name = name;
        this.stars = stars;
        this.page_url = page_url;
        this.photo = photo;
        this.facilities = facilities;
    }

    /**
     * Getter for the id of the hotel
     * @return id of the hotel
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for the name of the hotel
     * @return name of the hotel
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the hotel
     * @param name name of the hotel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the number of stars of the hotel
     * @return number of stars of the hotel
     */
    public int getStars() {
        return stars;
    }

    /**
     * Setter for the number of stars of the hotel
     * @param stars number of stars of the hotel
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * Getter for the url of the hotel
     * @return url of the hotel
     */
    public String getPage_url() {
        return page_url;
    }

    /**
     * Setter for the url of the hotel
     * @param page_url url of the hotel
     */
    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    /**
     * Getter for the photo of the hotel
     * @return photo of the hotel
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Setter for the photo of the hotel
     * @param photo photo of the hotel
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Getter for the list of facilities of the hotel
     * @return list of facilities of the hotel
     */
    public List<HotelFacility> getFacilities() {
        return facilities;
    }

    /**
     * Setter for the list of facilities of the hotel
     * @param facilities list of facilities of the hotel
     */
    public void setFacilities(List<HotelFacility> facilities) {
        this.facilities = facilities;
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
        Hotel hotel = (Hotel) o;
        return id == hotel.id;
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
        return "Hotel{" +
                "name='" + name + '\'' +
                ", stars=" + stars +
                ", photo='" + photo + '\'' +
                ", facilities=" + facilities +
                ", page_url='" + page_url + '\'' +
                '}';
    }
}
