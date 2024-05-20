package com.example.back.controller;

import com.example.back.Tools;
import com.example.back.model.Car;
import com.example.back.model.CarImage;
import com.example.back.model.File;
import com.example.back.model.Response;
import com.example.back.reponse.AuthResponse;
import com.example.back.reponse.CarResponse;
import com.example.back.repository.CarImageRepository;
import com.example.back.repository.CarRepository;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarImageRepository carImageRepository;

//    @RequestMapping("/getCar")
//    public ResponseEntity<Car> getCar(@RequestParam(name = "id") String id) {
//        return ResponseEntity.ok(carRepository.findFirstById(id));
//    }

    @RequestMapping("/getAllCar")
    public ResponseEntity<List<Car>> getAllCar() {
        List<Car> cars =  carRepository.findAll();
        cars.forEach(car -> {
            List<CarImage> carImages = carImageRepository.findCarImagesByCarId(car.getId());
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

    @RequestMapping("/upload")
    public ResponseEntity<Response> getUpload(@RequestParam(value = "fileList" , required = false) MultipartFile[] files,
                                              @RequestParam(value = "brandType") String brandType,
                                              @RequestParam(value = "type" , required = false) String type,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "carYear") Date carYear,
                                              @RequestParam(value = "comeYear" ) Date comeYear,
                                              @RequestParam(value = "motorPower") String motorPower,
                                              @RequestParam(value = "motorNumber") String motorNumber,
                                              @RequestParam(value = "seatNumber") Integer seatNumber,
                                              @RequestParam(value = "color" ) String color,
                                              @RequestParam(value = "km") Integer km,
                                              @RequestParam(value = "doorNumber") Integer doorNumber,
                                              @RequestParam(value = "price") BigDecimal price) throws IOException {
        try {
            Car car = new Car();
            car.setName(name);
            car.setType(type);
            car.setBrandType(brandType);
            car.setYear(carYear);
            car.setComeYear(comeYear);
            car.setMotorPower(motorPower);
            car.setMotorNumber(motorNumber);
            car.setSeatNumber(seatNumber);
            car.setColor(color);
            car.setKm(km);
            car.setDoorNumber(doorNumber);
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
}
