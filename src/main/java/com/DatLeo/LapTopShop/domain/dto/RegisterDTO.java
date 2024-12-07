package com.DatLeo.LapTopShop.domain.dto;

import com.DatLeo.LapTopShop.service.validator.RegisterChecked;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@RegisterChecked
public class RegisterDTO {
    
    @Size(min = 3, message = "First name phải có tối thiểu 3 ký tự")
    String  firstName;

    String lastName;

    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;
    
    @Size(min = 3, message = "Password phải có tối thiểu 3 ký tự")
    String password;
    String confirmPassword;

}
