package com.NikhilProject.Controller;

import com.NikhilProject.Model.Category;
import com.NikhilProject.Model.User;
import com.NikhilProject.Service.CategoryService;
import com.NikhilProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        Category createdCategory=categoryService.createCategory(category.getName(),user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);

    }
    @PostMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestBody Category category,
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

}
