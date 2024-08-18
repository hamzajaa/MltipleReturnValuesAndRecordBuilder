package com.example.mltiplereturnvalues;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultipleReturnValuesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleReturnValuesApplication.class, args);
    }

    public Pair<Person, Address> getInfoPair() {
        Person person = new Person("Hamza", 24);
        Address address = new Address("Rabat", "Morocco");
        return Pair.of(person, address);
    }

    public Pair<Person, Address> getInfoPairModify() {
        Person person = new Person("Hamza", 24);
        Address address = new Address("Rabat", "Morocco");
        return MutablePair.of(person, address);
    }

    public Triple<Person, Address, Location> getInfoTriple() {
        Person person = new Person("Ayoub", 30);
        Address address = new Address("Rabat", "Morocco");
        Location location = new Location(person, address);
        return Triple.of(person, address, location);
    }

    public MutableTriple<Person, Address, Location> getInfoTripleModify() {
        Person person = new Person("Ayoub", 30);
        Address address = new Address("Rabat", "Morocco");
        Location location = new Location(person, address);
        return MutableTriple.of(person, address, location);
    }


    @Bean
    ApplicationRunner init() {
        return args -> {
            Pair<Person, Address> infoPair = getInfoPair();
            System.out.println("infoPair = " + infoPair);

            Pair<Person, Address> infoPairModify = getInfoPairModify();
            infoPairModify.setValue(new Address("Casablanca", "Morocco"));
            System.out.println("infoPairModify = " + infoPairModify);

            Triple<Person, Address, Location> infoTriple = getInfoTriple();
            System.out.println("infoTriple = " + infoTriple);

            MutableTriple<Person, Address, Location> infoTripleModify = getInfoTripleModify();
            Address address = new Address("Casablanca", "Morocco");
            infoTripleModify.setMiddle(address);
            Location location = infoTripleModify.getRight();
//            location = LocationBuilder.builder(location).address(address).build();
            infoTripleModify.setRight(location.withAddress(address));
            System.out.println("infoTripleModify = " + infoTripleModify);

        };
    }

}

record Person(String name, int age) {
}

record Address(String city, String country) {
}

@RecordBuilder
record Location(Person person, Address address) implements LocationBuilder.With {
}
