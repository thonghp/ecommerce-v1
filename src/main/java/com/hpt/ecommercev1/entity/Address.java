package com.hpt.ecommercev1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    private String street;
    private String ward;
    private String district;
    private String city;
    private User user;
    @Override
    public String toString() {
        return street + " " + ward + " " + district + " " + city;
    }
}

