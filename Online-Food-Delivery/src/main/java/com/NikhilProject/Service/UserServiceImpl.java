package com.NikhilProject.Service;


import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Override
    public User findUserByJsonToken(String jwt) {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        User user=userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmailId(String email) {
     return userRepository.findByEmail(email);
    }
}
