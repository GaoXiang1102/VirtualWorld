package model;

import util.JsonUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LocalAccount on 2016/11/14.
 */
public class World {

    private Set<Location> all_locations = new HashSet<Location>();

    /**
     * read the information of all the locations to initialise the World model
     */
    public World() {
        this.all_locations = JsonUtil.getInstance().get_all_locations();
    }

    public Set<Location> getAll_locations() {
        return all_locations;
    }

    public void setAll_locations(Set<Location> all_locations) {
        this.all_locations = all_locations;
    }

    public boolean has_a_location(Location location) {
        return all_locations.contains(location);
    }
}
