package com.example.back.controller;

import com.example.back.model.Car;
import com.example.back.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;


    @RequestMapping("/getAllCar")
    public ResponseEntity<List<Car>> getAllCar() {
        return ResponseEntity.ok(carRepository.findAll());
    }
}
