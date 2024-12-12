package com.DatLeo.LapTopShop.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        // User chuyển hướng đến trang home sau khi login thành công
        roleTargetUrlMap.put("ROLE_USER", "/");
        // Admin chuyển hướng đến trang admin sau khi login thành công
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        // Kiểm tra quyền, so sánh
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

     // Dọn dẹp session tăng hiệu năng
    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        // Lấy thông tin email người dùng đã đăng nhập
        String email = authentication.getName();

        // Lấy thông tin người dùng bằng email
        User user = this.userService.getUserByEmail(email);

        if (email != null) {
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("avatar", user.getAvatar());
            session.setAttribute("id", user.getId());
            session.setAttribute("email", user.getEmail());

            int sum = user.getCart() == null ? 0 : user.getCart().getSum();
            session.setAttribute("sum", sum);
        }
        
    }

    // Config strategy after login
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
                String targetUrl = determineTargetUrl(authentication);

                if (response.isCommitted()) {
                    
                }

                redirectStrategy.sendRedirect(request, response, targetUrl);
                clearAuthenticationAttributes(request, authentication);
    }
    
}
