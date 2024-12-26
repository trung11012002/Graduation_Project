package com.cinema.notification.dto;

import java.security.Principal;

public class User1 implements Principal {

    private String name;

    public User1(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
