package com.NikhilProject.Controller;

import com.NikhilProject.Model.Order;
import com.NikhilProject.Model.User;
import com.NikhilProject.Service.OrderService;
import com.NikhilProject.Service.OrderServiceImpl;
import com.NikhilProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @PostMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        List<Order> order=orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{status}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Order order=orderService.updateOrder(id,order_status);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

}
