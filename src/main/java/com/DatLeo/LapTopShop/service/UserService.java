package com.DatLeo.LapTopShop.service;

import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Role;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.repository.RoleRepository;
import com.DatLeo.LapTopShop.repository.UserRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User handleSaveUser(User user){
        return this.userRepository.save(user);
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findRoleByName(name);
    }
    
}
