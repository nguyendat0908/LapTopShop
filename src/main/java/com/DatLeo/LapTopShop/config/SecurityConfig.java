package com.DatLeo.LapTopShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.DatLeo.LapTopShop.service.CustomUserDetailsService;
import com.DatLeo.LapTopShop.service.UserService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ghì đè lại method mặc định của lớp UserDetailsService mặc định là
    // InMemoryUserDetailsManager
    // Spring check password tại function additionalAuthenticationChecks
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    // Method kết hợp PasswordEncoder với CustomUserDetailService
    @Bean
    public DaoAuthenticationProvider authenProvider(PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    // Config redirect page after login success
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    // Config remember me
    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE).permitAll()
                        .requestMatchers("/", "/login", "/product/**", "/client/**", "/css/**", "/js/**", "/images/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                        
                // Config session
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Luôn tạo session mới
                        .invalidSessionUrl("/logout?expired") // Nếu session hết hạn thì tự động logout
                        .maximumSessions(1) // Tại một thời điểm có bao nhiêu tài khoản đăng nhập
                        .maxSessionsPreventsLogin(false)) // Khi 2 người đăng nhập cùng một tài khoản, người 2 đăng nhập
                                                          // sẽ đẩy người trước ra

                // Mỗi lần logout xóa Cookies và báo server Session hết hạn
                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

                .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler())
                        .permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

        return http.build();
    }
}
