package com.kostyuk.selenium.utils;

import com.github.javafaker.Faker;

import java.util.Map;

public class DataUtils {
    private static final Faker faker = new Faker();

    public static Map<String, String> generateUserTestData() {
        String name = faker.name().username();
        String password = faker.internet().password();
        String card = faker.finance().creditCard();
        return Map.of("name", name, "password", password, "card", card);
    }
}
