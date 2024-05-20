package com.example.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Document(collection = "car")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {
    @Id
    private String id;
    private String name;
    private String brandType;
    private String type;
    private BigDecimal price;
    private String motorPower;
    private String color;
    private String salonColor;
    @JsonFormat(pattern = "yyyy")
    private Date comeYear;
    @JsonFormat(pattern = "yyyy")
    private Date year;
    private int km;
    private int seatNumber;
    private int doorNumber;
    private String carNumber;
    private String motorNumber;
    @Transient
     List<CarImage> carImage;
    @DBRef
    private FuelType fuelType;
    @DBRef
    private UserPublicInfo user;
    @DBRef
    private List<Diagnostic> diagnosticList;

    public void add(List<CarImage> carImage) {
        this.carImage = carImage;
    }
}
