package com.DatLeo.LapTopShop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Role;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.domain.dto.RegisterDTO;
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

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findRoleByName(name);
    }

    public Page<User> getAllUserPage(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }

    public User getUserById(long id){
        return this.userRepository.findById(id);
    }

    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO){

        User user = new User();

        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        return user;
    }
    // Check email exist
    public boolean checkEmailExist(String email){
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }
    
}
