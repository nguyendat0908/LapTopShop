package com.DatLeo.LapTopShop.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.service.UploadFileService;
import com.DatLeo.LapTopShop.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;



@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {

        model.addAttribute("newUser", new User());

        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String postCreateUser(@ModelAttribute("newUser") User user, MultipartFile file) {

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        String avatar = this.uploadFileService.handleSaveUploadFile(file, "avatar");

        user.setPassword(hashPassword);
        user.setAvatar(avatar);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        this.userService.handleSaveUser(user);

        return "redirect:admin/user";
    }
    
    
}
