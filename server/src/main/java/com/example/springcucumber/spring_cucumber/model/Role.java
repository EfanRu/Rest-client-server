package com.example.springcucumber.spring_cucumber.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
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
