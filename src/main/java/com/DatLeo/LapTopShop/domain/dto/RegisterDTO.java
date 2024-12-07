package com.DatLeo.LapTopShop.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RegisterDTO {
    
    String  firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;

}
