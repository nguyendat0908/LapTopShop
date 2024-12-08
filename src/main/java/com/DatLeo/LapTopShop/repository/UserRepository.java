package com.DatLeo.LapTopShop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DatLeo.LapTopShop.domain.User;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    
    Page<User> findAll(Pageable pageable);

    User findById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);
    
}
