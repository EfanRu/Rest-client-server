package com.example.resttemplate.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private Integer id;
    private String name;
    private String authority;

    public Role() {}

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
        this.authority = "ROLE_" + name.toUpperCase();
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.authority = "ROLE_" + name.toUpperCase();
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name.toUpperCase();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
