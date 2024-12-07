package com.NikhilProject.Controller;

import com.NikhilProject.Model.Cart;
import com.NikhilProject.Model.USER_ROLE;
import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.CartRepository;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.Response.AuthResponse;
import com.NikhilProject.Service.CustomerDetailsService;
import com.NikhilProject.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private CartRepository cartRepository;
    @RequestMapping(method = RequestMethod.POST,path = "/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
        User isEmailExists=userRepository.findByEmail(user.getEmail());
        if(isEmailExists!=null){
            throw new Exception("Email is already used with another account");
        }

        User createdUser=new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser=userRepository.save(createdUser);

        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Success");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);


    }
    @RequestMapping(method = RequestMethod.POST,path = "/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody User user) throws Exception{

        UserDetails searchUser=customerDetailsService.loadUserByUsername(user.getEmail()); // This method is written a bit different by me so this may cause some issue please once have a look into this.
        if(searchUser==null){
            throw new UsernameNotFoundException("User doesn't exist with the User Name"+user.getEmail());
        }
        String passwordFetchedFromDatabase=searchUser.getPassword();
        if(!passwordEncoder.matches(user.getPassword(),passwordFetchedFromDatabase))
            throw  new Exception("Password you Entered is Wrong");

        Authentication auth=new UsernamePasswordAuthenticationToken(user,null,searchUser.getAuthorities());
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt=jwtProvider.generateToken(auth);
        AuthResponse response=new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("Successfully Logged In");
        response.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
