package com.DatLeo.LapTopShop.service.userInfo;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Role;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.service.UserService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // Call Api
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Get provider
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Process oAuth2User or map it to your local user database
        String email = (String) attributes.get("email");
        String fullName = (String) attributes.get("name");

        Role userRole = this.userService.getRoleByName("USER");

        if (email != null) {
            User user = this.userService.getUserByEmail(email);
            if (user == null) {
                // Create User
                User oUser = new User();
                oUser.setEmail(email);
                oUser.setAvatar("google-logo.png");
                oUser.setFullName(fullName);
                oUser.setProvider(
                        registrationId.equalsIgnoreCase("github") ? "GITHUB" : "GOOGLE");
                oUser.setPassword("null");
                oUser.setRole(userRole);

                this.userService.saveUser(oUser);
            } else {
                if (!user.getProvider().equalsIgnoreCase(registrationId)) {
                    OAuth2Error error = new OAuth2Error("invalid_request",
                            "Can't use this email address. Account already exist : " + email, null);
                    throw new OAuth2AuthenticationException(error);
                }
            }
        }

        // Handler exception
        if (email == null) {
            OAuth2Error error = new OAuth2Error("Invalid_request",
                    "Can't get email address. Maybe login with private email (Github)", null);
            throw new OAuth2AuthenticationException(error);
        }

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole.getName())),
                oAuth2User.getAttributes(), "email");
    }

}
