package com.NikhilProject.request;

import com.NikhilProject.Model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegeterian;
    private boolean seasonal;
}
