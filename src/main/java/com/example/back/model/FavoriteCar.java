package com.example.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "favoriteCar")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteCar {
    private User user;
    private String carId;
    @Transient
    Car car;
}