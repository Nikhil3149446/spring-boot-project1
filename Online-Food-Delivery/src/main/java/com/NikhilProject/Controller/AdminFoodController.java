package com.NikhilProject.Controller;

import com.NikhilProject.Model.Food;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.FoodRepository;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.Response.FoodResponse;
import com.NikhilProject.Service.FoodService;
import com.NikhilProject.Service.RestaurantService;
import com.NikhilProject.Service.UserService;
import com.NikhilProject.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class AdminFoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization")
                                           String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food=foodService.createFood(req,req.getCategory(),restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<FoodResponse> deleteFood(@PathVariable Long id) throws Exception {

        Food food=foodService.deleteFood(id);
        FoodResponse foodResponse=new FoodResponse();
        foodResponse.setMessage("Food Deleted Successfully");
        return new ResponseEntity<>(foodResponse,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id) throws Exception {
        Food food=foodService.updateFoodStatusByAvailability(id);
        return new ResponseEntity<>(food,HttpStatus.OK);
    }

}
