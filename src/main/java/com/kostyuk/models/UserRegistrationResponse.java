package com.kostyuk.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegistrationResponse {
    private String id;
    private String token;
}
