package com.example.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "car")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {
    @Id
    private String id;
    private String name;
//    @DBRef
//    private BrandType brandType;
//    @DBRef
//    private FuelType fuelType;
    @DBRef
    private User user;

}
