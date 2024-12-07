package com.NikhilProject.Controller;

import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.Service.UserService;
import com.NikhilProject.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserWithJwtToken(@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJsonToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
