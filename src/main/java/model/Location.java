package model;

/**
 * Created by LocalAccount on 2016/11/14.
 */
public class Location {

    public Location() {}

    public Location(String location_number) {
        this.location_number = location_number;
    }

    private String location_number;


    public String getLocation_number() {
        return location_number;
    }

    public void setLocation_number(String location_number) {
        this.location_number = location_number;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return location_number != null ? location_number.equals(location.location_number) : location.location_number == null;

    }

    @Override
    public int hashCode() {
        return location_number != null ? location_number.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.location_number;
    }
}
