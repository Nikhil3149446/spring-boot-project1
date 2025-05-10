package com.NikhilProject.Repository;

import com.NikhilProject.Model.Cart;
import com.NikhilProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("Select c from Cart c where c.customer.id= :userId")
    public Cart findUserById(@Param("userId") Long id);
}
