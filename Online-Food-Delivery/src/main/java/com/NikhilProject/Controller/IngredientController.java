package com.NikhilProject.Controller;

import com.NikhilProject.Model.IngredientCategory;
import com.NikhilProject.Model.IngredientsItem;
import com.NikhilProject.Service.IngredientsService;
import com.NikhilProject.request.IngredientCategoryRequest;
import com.NikhilProject.request.IngredientItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    IngredientsService ingredientsService;
    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest ingredientCategoryRequest
            ) throws Exception {
       IngredientCategory ingredientCategory=ingredientsService.createIngredientCategory(ingredientCategoryRequest.getName(),
               ingredientCategoryRequest.getRestaurantId());
       return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }
    @PostMapping("/items")
    public ResponseEntity<IngredientsItem> createIngredientItems(
            @RequestBody IngredientItemRequest ingredientItemRequest
    ) throws Exception {
        IngredientsItem ingredientsItem=ingredientsService.createIngredientItem(ingredientItemRequest.getRestaurantId(),
                ingredientItemRequest.getName(),ingredientItemRequest.getCategoryId());
        return new ResponseEntity<>(ingredientsItem,HttpStatus.CREATED);
    }
    @PutMapping("/stock/{id}")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem ingredientsItem=ingredientsService.updateStock(id);
        return new ResponseEntity<>(ingredientsItem,HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> ingredientsItem=ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(ingredientsItem,HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<IngredientCategory> getRestaurantIngredientCategory(
            @PathVariable Long id
    )throws Exception{
        IngredientCategory items=ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }



}
