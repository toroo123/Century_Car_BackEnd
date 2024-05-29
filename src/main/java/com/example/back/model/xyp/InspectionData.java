package com.example.back.model.xyp;

import lombok.Data;

@Data
public class InspectionData {

    private String cabinNumber;
    private String checkDate;
    private String expireDate;
    private String passed;
    private String resultCode;
    private String vehicleNumber;

}
