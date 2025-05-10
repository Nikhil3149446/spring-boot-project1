package com.NikhilProject.Service;

import com.NikhilProject.Model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    public Category createCategory(String name,Long id) throws Exception;
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;
    public Category findCategoryById(Long id) throws Exception;
}
