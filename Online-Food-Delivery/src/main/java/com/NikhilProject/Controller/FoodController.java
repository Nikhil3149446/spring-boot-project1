package com.NikhilProject.Controller;

import com.NikhilProject.Model.Food;
import com.NikhilProject.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class FoodController {
    @Autowired
    FoodService foodService;
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name){
        List<Food> food=foodService.searchFood(name);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vegeterian,
            @RequestParam boolean seasonal,
            @RequestParam(required = false) String foodCategory,
            @PathVariable(name = "restaurantId") Long id
    ){
        List<Food> listOfFood=foodService.getRestaurantsFood(id,vegeterian,seasonal,foodCategory);
        return new ResponseEntity<>(listOfFood,HttpStatus.OK);
    }

}
