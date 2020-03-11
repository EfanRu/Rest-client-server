package com.example.springcucumber.spring_cucumber.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @JsonIgnore
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
