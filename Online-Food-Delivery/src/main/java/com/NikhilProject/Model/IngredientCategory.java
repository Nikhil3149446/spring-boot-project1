package com.NikhilProject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    @Autowired
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<IngredientsItem> ingredientsItems;



}
