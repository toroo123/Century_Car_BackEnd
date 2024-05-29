package com.example.back.model.xyp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomField {

    private String plateNumber;
    private String cabinNumber;

}
