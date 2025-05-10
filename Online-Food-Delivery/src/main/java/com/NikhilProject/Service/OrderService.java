package com.NikhilProject.Service;

import com.NikhilProject.Model.Order;
import com.NikhilProject.Model.User;
import com.NikhilProject.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest request, User user) throws Exception;
    public Order updateOrder(Long id,String orderStatus) throws Exception;
    public Order cancelOrder(Long id) throws Exception;
    public List<Order> getUserOrder(Long userId) throws Exception;
    public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus) throws Exception;
}
