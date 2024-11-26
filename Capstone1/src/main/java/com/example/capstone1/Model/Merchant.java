package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "the Merchant ID cant be empty ")
    private String id;
    @NotEmpty(message = "Merchant name cant be empty")
    @Size(min = 4,message = "the category name cant be less than 4")
    private String name;
    private boolean active=true;
    private String city;
    private String country;
}
