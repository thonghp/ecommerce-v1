package com.hpt.ecommercev1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    private Integer id;
    private String imagePath;
    private String name;
    private Date createdTime;
    private boolean enabled;

    public Banner(String imagePath, String name, boolean enabled) {
        this.imagePath = imagePath;
        this.name = name;
        this.enabled = enabled;
    }

    public Banner(String imagePath, String name, Date createdTime, boolean enabled) {
        this.imagePath = imagePath;
        this.name = name;
        this.createdTime = createdTime;
        this.enabled = enabled;
    }
}