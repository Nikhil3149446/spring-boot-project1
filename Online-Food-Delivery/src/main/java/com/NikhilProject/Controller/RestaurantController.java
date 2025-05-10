package com.NikhilProject.Controller;

import com.NikhilProject.Dto.RestaurantDto;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Model.User;
import com.NikhilProject.Service.RestaurantService;
import com.NikhilProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RestaurantController {

    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;
    @GetMapping("/search/restaurant")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ){
      List<Restaurant> restaurant=restaurantService.searchRestaurant(keyword);
      return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> findAllRestaurant(){
        List<Restaurant> restaurant=restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @PathVariable Long id
    ) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
    @GetMapping("/{id}/add-to-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(@RequestHeader String jwt,
                                                          @PathVariable Long id) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        RestaurantDto restaurantDto=restaurantService.addToFavourites(id,user);
        return new ResponseEntity<>(restaurantDto,HttpStatus.CREATED);

    }
}
