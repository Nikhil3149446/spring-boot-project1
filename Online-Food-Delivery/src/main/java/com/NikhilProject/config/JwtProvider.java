package com.NikhilProject.config;

import com.NikhilProject.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Slf4j
@Component
public class JwtProvider {
    public JwtProvider(){}
    private SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.toString().getBytes());

    public String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthorities(authorities);

        String jwt= Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        log.info("The claims for the jwt is {}",claims);
        String email=String.valueOf(claims.get("email"));
        log.info("The email for the jwt is {}",email.substring(email.lastIndexOf("email=")+6,email.indexOf(",",email.lastIndexOf("email="))));
        return  email.substring(email.lastIndexOf("email=")+6,email.indexOf(",",email.lastIndexOf("email=")));
    }


    public String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
      Set<String> auth=new HashSet<>();
      for(GrantedAuthority authority:authorities){
          auth.add(authority.getAuthority());
      }
      return String.join(",",auth);
    }
}
