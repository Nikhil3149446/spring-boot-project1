package com.NikhilProject.Service;

import com.NikhilProject.Dto.RestaurantDto;
import com.NikhilProject.Model.Address;
import com.NikhilProject.Model.Restaurant;
import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.AddressRepository;
import com.NikhilProject.Repository.RestaurantRepository;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address=addressRepository.save(req.getAddress());

        Restaurant restaurant=new Restaurant();
        restaurant.setId(req.getId());
        restaurant.setAddress(req.getAddress());
        restaurant.setCuisineType(req.getCrusineType());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(id);

        if(restaurant.getCuisineType()!=null)
            restaurant.setCuisineType(updatedRestaurant.getCrusineType());
        if(restaurant.getDescription()!=null)
            restaurant.setDescription(updatedRestaurant.getDescription());
        if(restaurant.getName()!=null)
            restaurant.setName(updatedRestaurant.getName());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {

        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyWord) {
        return restaurantRepository.findByUserQuery(keyWord);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> optionalRestaurant= restaurantRepository.findById(id);
        if(optionalRestaurant.isEmpty())
            throw new Exception("Couldn't find Restaurant");
        return optionalRestaurant.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long id) throws Exception {
        Restaurant restaurant= restaurantRepository.findByOwnerId(id);
        if(restaurant==null)
            throw new Exception("Could'nt find Restaurant");
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        RestaurantDto restaurantDto=new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        if(user.getFavourites().stream().anyMatch(dto->dto.equals(restaurantDto)))
            user.getFavourites().remove(restaurantDto);
        else user.getFavourites().add(restaurantDto);

        userRepository.save(user);
        return restaurantDto;

    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurant;
    }
}
