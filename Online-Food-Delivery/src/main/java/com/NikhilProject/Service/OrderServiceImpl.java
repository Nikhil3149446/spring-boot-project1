package com.NikhilProject.Service;

import com.NikhilProject.Model.*;
import com.NikhilProject.Repository.OrderRepository;
import com.NikhilProject.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CartService cartService;
    @Override
    public Order createOrder(OrderRequest request, User user) throws Exception {
        Order order=new Order();
        Restaurant restaurant=restaurantService.findRestaurantById(request.getRestaurantId());
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setCreatedAt(new Date());
        order.setOrderStatus("PENDING");
        Cart cart=cartService.findCartByUserId(user.getId());
        List<CartItem> cartItems=cart.getItem();
        List<OrderItem> orderItems=new ArrayList<>();
        Long totalAmount=0L;
        for(CartItem cartItem:cartItems){
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItems.add(orderItem);
            totalAmount+=cartItem.getTotalPrice();

        }
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        restaurant.getOrders().add(order);
        return order;
    }

    @Override
    public Order updateOrder(Long id, String orderStatus) throws Exception {
        Optional<Order> fetchedOrder=orderRepository.findById(id);
        if(fetchedOrder.isEmpty())
            throw new Exception("Order not found");
        Order order=fetchedOrder.get();
        order.setOrderStatus(orderStatus);
        return order;
    }

    @Override
    public Order cancelOrder(Long id) throws Exception {
        Optional<Order> fetchedOrder=orderRepository.findById(id);
        if(fetchedOrder.isEmpty())
            throw new Exception("Order not Found");
        Order order=fetchedOrder.get();
        orderRepository.deleteById(id);
        return order;
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> listOfOrders=orderRepository.findByRestaurantId(restaurantId);
        List<Order> orders=listOfOrders.stream().filter(order -> order.getOrderStatus()
                .equals(orderStatus)).toList();
        return orders;
    }
}
