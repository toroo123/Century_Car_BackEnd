package com.example.back.feign;

import com.example.back.model.xyp.CarData;
import com.example.back.model.xyp.InspectionData;
import com.example.back.model.xyp.RequestData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name="xypPublic", url="https://xyp-api.smartcar.mn/xyp-api/v1/xyp")
public interface XypPublic {

    @PostMapping("get-data-public")
    CarData getCarData(@RequestBody RequestData requestData);

    @PostMapping("get-data-public")
    InspectionData getDiagnosticData(@RequestBody RequestData requestData);

}
