package com.NikhilProject.Service;


import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.config.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Override
    public User findUserByJsonToken(String jwt) {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        log.info("The email fetched from the JwtToken is {}",email);
        User user=userRepository.findByEmail(email);
        log.info("The user fetched from the useremail is {}",user);
        return user;
    }

    @Override
    public User findUserByEmailId(String email) {
     return userRepository.findByEmail(email);
    }
}
