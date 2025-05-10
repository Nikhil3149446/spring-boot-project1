package com.NikhilProject.Controller;

import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.RestaurantRepository;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.Response.MessageResponse;
import com.NikhilProject.Service.RestaurantService;
import com.NikhilProject.Service.UserService;
import com.NikhilProject.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/restaurants")
@Controller
public class AdminRestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader String jwt
            ) throws Exception{
        User user=userService.findUserByJsonToken(jwt);
        Restaurant restaurant=restaurantService.createRestaurant(req,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestBody Long id) throws Exception {
           Restaurant restaurant=restaurantService.findRestaurantById(id);
           restaurantService.deleteRestaurant(id);
           MessageResponse messageResponse=new MessageResponse();
           messageResponse.setMessage("Restaurant Deleted Successfully");
           return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader String jwt,
            @PathVariable Long id
    ) throws Exception{
        User user=userService.findUserByJsonToken(jwt);

        Restaurant restaurant=restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id) throws Exception {
        Restaurant restaurant=restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);

    }
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestBody CreateRestaurantRequest req,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(user.getId());
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
}
