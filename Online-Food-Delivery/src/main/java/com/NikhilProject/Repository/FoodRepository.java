package com.NikhilProject.Repository;

import com.NikhilProject.Model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {

    public List<Food> findByRestaurantId(Long restaurantId);
    @Query("Select f from Food f where f.id= :id")
    public Food findFoodByFoodId(@Param("id") Long id);
    @Query("Select f from Food f where f.name like %:keyword% OR f.foodCategory.name like %:keyword%")
    public List<Food> searchFood(@Param("keyword") String keyword);

}
