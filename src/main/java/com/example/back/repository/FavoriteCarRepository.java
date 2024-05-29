package com.example.back.repository;

import com.example.back.model.FavoriteCar;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ComponentScan
public interface FavoriteCarRepository extends MongoRepository<FavoriteCar,String> {

    @Query("{carId :?0}")
    List<FavoriteCar> findByCarId(String carId);

    @Query(value = "{carId :?0}", delete = true)
    void deleteByCarId(String carId);

}
