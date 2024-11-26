package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class MerchantStock {
    @NotEmpty(message = "the category ID cant be empty ")
    private String id;
    @NotEmpty(message = "product ID name cant be empty")
    private String productID;
    @NotEmpty(message = "merchant ID name cant be empty")
    private String merchantID;
    @NotEmpty(message = "stock cant be empty")
    @Min(value = 10,message = "stock cant be less than 10 in  start")
    private int stock;

}
