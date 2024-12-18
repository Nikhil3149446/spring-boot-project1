package com.NikhilProject.Repository;

import com.NikhilProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String username);
}
