package com.example.back.repository;

import com.example.back.model.CarImage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@ComponentScan

public interface CarImageRepository extends MongoRepository<CarImage,String> {
    List<CarImage> findAll();

    CarImage findFirstById(String id);
    List<CarImage> findCarImagesByCarId(String carId);

    @Query("select a from CarImage a where a.carId in ?1 ")
    List<CarImage>  findCarById(String id);

}
