package com.NikhilProject.Service;


import com.NikhilProject.Model.Category;
import com.NikhilProject.Model.Food;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Repository.FoodRepository;
import com.NikhilProject.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;
    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food=new Food();
        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setFoodCategory(req.getCategory());
        food.setCreationDate(new Date());
        food.setImages(req.getImages());
        food.setRestaurant(restaurant);
        food.setVegetarian(req.isVegeterian());
        food.setSeasonal(req.isSeasonal());
        foodRepository.save(food);
        restaurant.getFoods().add(food);
        return food;
    }

    @Override
    public Food deleteFood(Long foodId) throws Exception {
        // This method is implemented in a different way in the Owner Code I have done it Differently
        Optional<Food> foodDelete=foodRepository.findById(foodId);
        if(foodDelete.isEmpty())
            throw new Exception();
        else {
            foodRepository.delete(foodDelete.get());
        }

        return foodDelete.get();
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegeterian, boolean isSeasonal, String foodCategory) {
        List<Food> allFood=foodRepository.findAll();
        List<Food> food=allFood.stream().filter(f->f.isVegetarian()==isVegeterian && f.isSeasonal()==isSeasonal)
                .filter(f->f.getRestaurant().getId()==restaurantId && f.getFoodCategory().equals(foodCategory))
                .collect(Collectors.toList());
        return food;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        List<Food> foods=foodRepository.searchFood(keyword);
        return foods;
    }

    @Override
    public Food findFoodById(Long id) throws Exception {
        Food food=foodRepository.findFoodByFoodId(id);
        if(food==null)
            throw new Exception("No food found with the given Id");
        return food;
    }

    @Override
    public Food updateFoodStatusByAvailability(Long foodId) throws Exception {

        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;
    }
}
