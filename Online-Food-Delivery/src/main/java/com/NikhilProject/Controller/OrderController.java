package com.NikhilProject.Controller;

import com.NikhilProject.Model.Order;
import com.NikhilProject.Model.User;
import com.NikhilProject.Service.OrderService;
import com.NikhilProject.Service.UserService;
import com.NikhilProject.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        Order order=orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequest request,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        List<Order> order=orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
