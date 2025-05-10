package com.NikhilProject.Service;

import com.NikhilProject.Model.Category;
import com.NikhilProject.Model.Food;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
    public Food deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantsFood(Long restaurantId,boolean isVegeterian,boolean isSeasonal,
                                         String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long id) throws Exception;
    public Food updateFoodStatusByAvailability(Long foodId) throws Exception;


}
