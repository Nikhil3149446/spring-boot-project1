package com.NikhilProject.Model;

import com.NikhilProject.Dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String reTypePassword;
    private USER_ROLE role;
    @Autowired
    @JsonIgnore     // because whenever i fetch the user i dont need to fetch his order basically it ignores this field whenever we call the User data
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders;
    @Autowired
    @ElementCollection
    private List<RestaurantDto> favourites;
    @Autowired
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;



}
