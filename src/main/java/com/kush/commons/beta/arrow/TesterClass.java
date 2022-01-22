package com.kush.commons.beta.arrow;

import static com.kush.commons.beta.arrow.sample.Person.person;

import com.kush.commons.beta.arrow.sample.City;
import com.kush.commons.beta.arrow.sample.Person;
import com.kush.commons.beta.arrow.sample.PersonVectorizer;

public class TesterClass {

    public static void main(String[] args) {
        var city1 = City.city("City1", "State1");
        personWriter("persons.arrow").write(
                person("Person1", 20, city1),
                person("Person2", 30, city1),
                person("Person3", 40, city1));
    }

    private static ChunkedWriter<Person> personWriter(String file) {
        Vectorizer<Person> personVectorizer = new PersonVectorizer();
        return new ChunkedWriter<>(file, personVectorizer, 2);
    }
}
