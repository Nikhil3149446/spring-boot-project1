package com.NikhilProject.Service;

import com.NikhilProject.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findUserByJsonToken(String jwt);
    public User findUserByEmailId(String email);
}
