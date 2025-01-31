package com.kostyuk.api.utils;

import java.util.Map;

public class TestUtils {

    public static Map<String, String> generateCorrectUserRegistrationData() {
        //В связи с ограниченностью функционала Reqres здесь задаются конкретные значения, а не генерируются случайные
        return Map.of("email", "eve.holt@reqres.in", "password", "pistol");
    }

    public static Map<String, String> generateCorrectUserUpdateData() {
        return Map.of("name", "morpheus", "job", "zion resident");
    }
}
