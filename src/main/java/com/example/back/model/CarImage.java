package com.example.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "carImage")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarImage {

    @Id
    private String id;
    private String carId;
    private String userId;
    private String name;
    private String type;
    private Date date;
    private String base64Url;
    private Long status;
}
