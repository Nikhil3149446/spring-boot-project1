package com.NikhilProject.Service;

import com.NikhilProject.Model.IngredientCategory;
import com.NikhilProject.Model.IngredientsItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
    public IngredientCategory findIngredientCategoryByRestaurantId(Long id) throws Exception;
    public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;
    public IngredientsItem updateStock(Long id) throws Exception;
}
