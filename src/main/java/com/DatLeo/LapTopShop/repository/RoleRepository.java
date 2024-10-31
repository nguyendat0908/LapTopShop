package com.DatLeo.LapTopShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DatLeo.LapTopShop.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findRoleByName(String name);
}
