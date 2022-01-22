package com.kush.commons.beta.arrow.sample;

import static com.kush.commons.beta.arrow.sample.City.city;

public class Person {
    public String name;
    public int age;
    public City city;

    public static Person person(String name, int age, City city) {
        var person = new Person();
        person.name = name;
        person.age = age;
        person.city = city;
        return person;
    }

    public static Person person(String name, int age, String city, String state) {
        return person(name, age, city(city, state));
    }
}
