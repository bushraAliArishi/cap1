package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "ID cnat be empty")
    private String id;
    @NotEmpty(message = "user name cant be empty")
    @Size(min = 5,message = " have to be more than 5 length long")
    private String userName;
    @NotEmpty(message = "passwrod cant be empty")
    @Size(min = 5,message = " have to be more than 5 length long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain at least one letter and one digit")
    private String password;
    @NotEmpty(message = "email cant be empty")
    @Email(message = "you need to enter valid email ")
    private String email;
    @NotNull(message = "Role cannot be null")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either 'Admin' or 'Customer'")
    private String role;
    @NotNull(message = "the balance cant be empty")
    @PositiveOrZero(message = "must be positive number or zero")
    private double balance;
    private ArrayList<Product> visitHistory;


}
