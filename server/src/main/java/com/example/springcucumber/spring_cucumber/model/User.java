package com.example.springcucumber.spring_cucumber.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    @Transient
    private boolean enabled = true;

    public User() {}

    public User(Long id, String firstName, String lastName, String login, String password, Long phoneNumber, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public User(String firstName, String lastName, String login, String password, Long phoneNumber, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.id = null;
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getName().toUpperCase()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User id=")
                .append(getId())
                .append(", first name=")
                .append(getFirstName())
                .append(", last name=")
                .append(getLastName())
                .append(", role=")
                .append(getRole())
                .append(", login=")
                .append(getLogin())
                .append(", password=")
                .append(getPassword())
                .toString();
    }
}
