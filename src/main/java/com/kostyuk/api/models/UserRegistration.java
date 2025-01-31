package com.kostyuk.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistration {
    private final String email;
    private final String password;
}
