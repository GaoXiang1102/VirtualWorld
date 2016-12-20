package model;

/**
 * Created by LocalAccount on 2016/11/14.
 */
public enum Direction {
    N("N"), WN("WN"), W("W"), WS("WS"), S("S"), SE("SE"), E("E"), NE("NE");

    private String direction_name;

    Direction(String direction_name) {
        this.direction_name = direction_name;
    }

    public String getDirection_name() {
        return direction_name;
    }

    public void setDirection_name(String direction_name) {
        this.direction_name = direction_name;
    }
}
