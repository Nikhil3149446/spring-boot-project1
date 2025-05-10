package com.NikhilProject.Repository;

import com.NikhilProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User,Long> {
    @Query("Select u from User u where u.email= :username")
    public User findByEmail(@Param("username") String username);
}
