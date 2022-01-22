package com.kush.commons.beta.arrow.sample;

public class City {
    public String cityName;
    public String stateName;

    public static City city(String name, String state) {
        var city = new City();
        city.cityName = name;
        city.stateName = state;
        return city;
    }
}
