package com.NikhilProject.Service;

import com.NikhilProject.Dto.RestaurantDto;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Model.User;
import com.NikhilProject.request.CreateRestaurantRequest;
import org.springframework.stereotype.Service;

import java.util.List;
public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long id,CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyWord);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long id) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantId,User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
