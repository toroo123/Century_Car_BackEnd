package com.example.back.controller;

import com.example.back.Tools;
import com.example.back.feign.XypPublic;
import com.example.back.model.*;
import com.example.back.model.xyp.CarData;
import com.example.back.model.xyp.CustomField;
import com.example.back.model.xyp.RequestData;
import com.example.back.reponse.AuthResponse;
import com.example.back.reponse.CarResponse;
import com.example.back.repository.CarImageRepository;
import com.example.back.repository.CarRepository;
import com.example.back.repository.FavoriteCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarImageRepository carImageRepository;
    @Autowired
    private FavoriteCarRepository favoriteCarRepository;
    @Autowired
    private XypPublic xypPublic;

    private final String vehicleInfo = "WS100401_getVehicleInfo";
    private final String inspectionInfo = "WS100409_getVehicleInspectionInfo";

//    @RequestMapping("/getCar")
//    public ResponseEntity<Car> getCar(@RequestParam(name = "id") String id) {
//        return ResponseEntity.ok(carRepository.findFirstById(id));
//    }

    @RequestMapping("/getAllCar")
    public ResponseEntity<List<Car>> getAllCar() {
        List<Car> cars =  carRepository.findAll();
        cars.forEach(car -> {
            List<CarImage> carImages = carImageRepository.findCarImagesByCarId(car.getId());
            car.setFavorite(!favoriteCarRepository.findByCarId(car.getId()).isEmpty());
            car.setCarImage(carImages);
        });
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/getCar/{id}")
    public ResponseEntity<Car> getCar(@PathVariable String id) {
        Car car = carRepository.findFirstById(id);
        List<CarImage> carImages = carImageRepository.findCarImagesByCarId(car.getId());
        car.setCarImage(carImages);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/favorite")
    public ResponseEntity<FavoriteCar> getFavorite(@RequestParam String carId) {
        FavoriteCar favCar = new FavoriteCar();
        favCar.setCarId(carId);
        List<FavoriteCar> exist = favoriteCarRepository.findByCarId(carId);
        if (exist.isEmpty())
            favoriteCarRepository.save(favCar);
        else
            favoriteCarRepository.deleteByCarId(carId);

        return ResponseEntity.ok(favCar);
    }

    @RequestMapping("/getAllFavoriteCar")
    public ResponseEntity<List<FavoriteCar>> getAllFavoriteCar() {
        List<FavoriteCar> favCars =  favoriteCarRepository.findAll();
        favCars.forEach(favCar -> {
            Car car = carRepository.findFirstById(favCar.getCarId());
            List<CarImage> carImages = carImageRepository.findCarImagesByCarId(car.getId());
            car.setCarImage(carImages);
            favCar.setCar(car);
        });
        return ResponseEntity.ok(favCars);
    }

    @RequestMapping("/upload")
    public ResponseEntity<Response> getUpload(@RequestParam(value = "fileList" , required = false) MultipartFile[] files,
                                              @RequestParam(value = "markName") String markName,
                                              @RequestParam(value = "countryName" , required = false) String countryName,
                                              @RequestParam(value = "modelName") String modelName,
                                              @RequestParam(value = "buildYear") String buildYear,
                                              @RequestParam(value = "importYear" ) Date importYear,
                                              @RequestParam(value = "capacity") String capacity,
                                              @RequestParam(value = "cabinNumber") String cabinNumber,
                                              @RequestParam(value = "manCount") Integer manCount,
                                              @RequestParam(value = "colorName" ) String colorName,
                                              @RequestParam(value = "fuelType" ) String fuelType,
                                              @RequestParam(value = "passed", required = false) Boolean passed,
                                              @RequestParam(value = "checkDate" , required = false) Date checkDate,
                                              @RequestParam(value = "expireDate" , required = false) Date expireDate,
                                              @RequestParam(value = "phone" ) String phone,
                                              @RequestParam(value = "km") Integer km,
                                              @RequestParam(value = "price") BigDecimal price) throws IOException {
        try {
            Car car = new Car();
            car.setName(modelName);
            car.setCountryName(countryName);
            car.setBrandType(markName);
            car.setYear(buildYear);
            car.setComeYear(importYear);
            car.setMotorPower(capacity);
            car.setMotorNumber(cabinNumber);
            car.setSeatNumber(manCount);
            car.setColor(colorName);
            car.setFuelType(fuelType);
            car.setPassed(passed);
            car.setCheckDate(checkDate);
            car.setExpireDate(expireDate);
            car.setPhone(phone);
            car.setKm(km);
            car.setPrice(price);
            String carId = carRepository.save(car).getId();

            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    CarImage image = new CarImage();
                    image.setBase64Url("data:image/png;base64,"+ Base64.getEncoder().encodeToString(file.getBytes()));
                    image.setName(file.getOriginalFilename());
                    image.setCarId(carId);
                    carImageRepository.save(image);
                }
            }
            return Tools.getResponseModel("sucess");
        } catch (IOException e) {
            return Tools.getResponseModel("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("getCarXypData")
    public ResponseEntity<CarData> getCarXypData(@RequestParam String plateNumber) {
        RequestData requestData = new RequestData();
        requestData.setServiceCode(vehicleInfo);
        CustomField vehicleRequest = new CustomField();
        vehicleRequest.setPlateNumber(plateNumber);
        requestData.setCustomFields(vehicleRequest);
        CarData carData = xypPublic.getCarData(requestData);

        CustomField inspectionRequest = new CustomField();
        inspectionRequest.setCabinNumber(carData.getCabinNumber());
        requestData.setServiceCode(inspectionInfo);
        requestData.setCustomFields(inspectionRequest);
        carData.setInspectionData(xypPublic.getDiagnosticData(requestData));
        return ResponseEntity.ok(carData);
    }
}