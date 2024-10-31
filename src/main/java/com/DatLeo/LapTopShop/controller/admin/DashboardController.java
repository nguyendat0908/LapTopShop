package com.DatLeo.LapTopShop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.DatLeo.LapTopShop.service.DashboardService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;

    @GetMapping("/admin/dashboard")
    public String getDashboardPage(Model model) {

        long countProduct = this.dashboardService.countProducts();
        long countUser = this.dashboardService.countUsers();

        model.addAttribute("countProduct", countProduct);
        model.addAttribute("countUser", countUser);

        return "admin/dashboard/show";
    }
    
}