package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class HotelFacility {

    @Id
    private String name;

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
