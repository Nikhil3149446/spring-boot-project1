package com.NikhilProject.Service;

import com.NikhilProject.Model.Category;
import com.NikhilProject.Model.IngredientCategory;
import com.NikhilProject.Model.IngredientsItem;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Repository.IngredientCategoryRepository;
import com.NikhilProject.Repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IngredientsServiceImpl implements IngredientsService{
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    IngredientItemRepository ingredientItemRepository;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);
        ingredientCategoryRepository.save(ingredientCategory);
        return ingredientCategory;
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory= ingredientCategoryRepository.findById(id);
        if(ingredientCategory.isEmpty())
            throw new Exception("Couldn't find the Ingredient Category");

        return ingredientCategory.get();
    }

    @Override
    public IngredientCategory findIngredientCategoryByRestaurantId(Long id) throws Exception {
        List<IngredientCategory> listOfIngredientCategory=ingredientCategoryRepository.findAll();
        IngredientCategory ingredientCategory = listOfIngredientCategory.stream()
                .filter(ingredient -> ingredient.getRestaurant().getId().equals(id))
                .findFirst()
                .orElse(null);

        return ingredientCategory;
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        Category category=categoryService.findCategoryById(categoryId);
        IngredientCategory ingredientCategory=findIngredientCategoryByRestaurantId(restaurantId);
        IngredientsItem ingredientsItem=new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);
        ingredientsItem.setId(categoryId);
        ingredientItemRepository.save(ingredientsItem);
        ingredientCategory.getIngredientsItems().add(ingredientsItem);
        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> fetchedIngredientsItem=ingredientItemRepository.findById(id);
        if(fetchedIngredientsItem.isEmpty())
            throw new Exception("Couldn't find the Ingredients");
        IngredientsItem ingredientsItem=fetchedIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
