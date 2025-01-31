package com.kostyuk.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserBody {
    private final String name;
    private final String job;
}
