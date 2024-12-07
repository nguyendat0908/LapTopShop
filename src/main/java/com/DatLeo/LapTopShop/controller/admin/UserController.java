package com.DatLeo.LapTopShop.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.service.UploadFileService;
import com.DatLeo.LapTopShop.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;

    // Get create user page
    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {

        model.addAttribute("newUser", new User());

        return "admin/user/create";
    }

    // Save user
    @PostMapping("/admin/user/create")
    public String postCreateUser(@ModelAttribute("newUser") @Valid User user, BindingResult newUserBindingResult, @RequestParam("uploadFile") MultipartFile file) {

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        String avatar = this.uploadFileService.handleSaveUploadFile(file, "avatar");

        user.setPassword(hashPassword);
        user.setAvatar(avatar);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        this.userService.handleSaveUser(user);

        return "redirect:/admin/user";
    }
    
    // Get all user page
    @GetMapping("/admin/user")
    public String getAllUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<User> users = this.userService.getAllUserPage(pageable);
        List<User> listUsers = users.getContent();

        model.addAttribute("users", listUsers);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/user/show";
    }
    
    // Get detail user page
    @GetMapping("/admin/user/{id}")
    public String getDetailUserPage(Model model, @PathVariable long id) {

        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);

        return "admin/user/detailUser";
    }

    // Get update user page
    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {

        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);

        return "admin/user/update";
    }

    // Update user
    @PostMapping("/admin/user/update")
    public String postUpdateUser(@ModelAttribute("newUser") User user, @RequestParam("uploadFile") MultipartFile file) {

        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {

            // Get path old avatar
            String oldPathAvatar = currentUser.getAvatar();

            // Get full path
            String fullOldPathAvatar = this.uploadFileService.getFullPathFile(oldPathAvatar, "avatar");
            System.out.println("Full Old Path Avatar: " + fullOldPathAvatar);

            if (!file.isEmpty()) {
                
                this.uploadFileService.deleteFile(fullOldPathAvatar);

                String img = this.uploadFileService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(img);
            }

            currentUser.setFullName(user.getFullName());
            currentUser.setPassword(user.getPassword());
            currentUser.setPhone(user.getPhone());
            currentUser.setAddress(user.getAddress());

            this.userService.handleSaveUser(currentUser);
        }

        return "redirect:/admin/user";
    }

    // Delete User Page
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {

        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }
    
    // Delete User
    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User user) {

        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }
    
    
}