package com.example.resttemplate.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private Integer id;
    private String name;

    public Role() {}

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
