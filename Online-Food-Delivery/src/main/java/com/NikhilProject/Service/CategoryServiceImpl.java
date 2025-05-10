package com.NikhilProject.Service;

import com.NikhilProject.Model.Category;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public Category createCategory(String name, Long id) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        Category category=new Category();
        category.setRestaurant(restaurant);
        category.setName(name);
        categoryRepository.save(category);
        return category;

    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {

        List<Category> categories=categoryRepository.findAll().stream().filter(category -> category.getRestaurant().getOwner().getId().equals(id))
                .toList();
        return categories;


    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category=categoryRepository.findById(id);
        if(category.isEmpty())
            throw new Exception("Couldn't find the Category");
        return category.get();
    }
}
