package com.hpt.ecommercev1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String imagePath;
    private String email;
    private String password;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
    private Address address;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' +
                ", enabled=" + enabled + ", roles=" + roles + ", address=" + address + '}';
    }
}