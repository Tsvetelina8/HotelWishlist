package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class HotelFacility {

    @Id
    private String name;
    @ManyToMany(mappedBy = "facilities")
    private List<Hotel> hotels;

    /**
     * Default constructor, for object mapper
     */
    public HotelFacility() {}

    /**
     * Constructs a HotelFacility object with the given name
     * @param name name of the facility
     */
    public HotelFacility(String name) {
        this.name = name;
        hotels = new ArrayList<>();
    }

    public void addHotel(Hotel hotel) {
        if(!hotels.contains(hotel)) {
            hotels.add(hotel);
        }
    }

    /**
     * Getter for the name of the facility
     * @return name of the facility
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the facility
     * @param name name of the facility
     */
    public void setName(String name) {
        this.name = name;
    }

    public List<Hotel> getHotels() {
        return hotels;
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
        HotelFacility that = (HotelFacility) o;
        return Objects.equals(name, that.name);
    }

    /**
     * Method to get the hashcode of the object
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    /**
     * Method to get the string representation of the object
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "HotelFacility{" +
                "name='" + name + '\'' +
                '}';
    }
}
