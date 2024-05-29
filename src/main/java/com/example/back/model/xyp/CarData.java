package com.example.back.model.xyp;

import lombok.Data;

@Data
public class CarData {

    private String plateNumber;
    private String cabinNumber;
    private String countryName;
    private String markName;
    private String modelName;
    private Long buildYear;
    private String colorName;
    private String type;
    private String ownerType;
    private String intent;
    private String className;
    private String motorNumber;
    private String importDate;
    private String fuelType;
    private Long manCount;
    private Long axleCount;
    private Double capacity;
    private Double mass;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private String transmission;
    private String wheelPosition;
    private String rfid;
    private InspectionData inspectionData;

}
