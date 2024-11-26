package com.example.capstone1.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "the id cant be empty ")
    private String id;
    @NotEmpty(message = "the Product name cant be empty ")
    @Size(min = 3,message = "the Product name must be more than 3")
    private String name;
    @NotNull(message = "the price cant be null ")
    @Positive(message = "the price cant be a negative number")
    private double price;
    @NotEmpty(message = "the category id cant be empty ")
    private String categoryID;

    private boolean FlashSale;

    private double flashSalePrice = 0.0;

    private int flashSaleDuration = 0;

    private int buyCount=0;
    @JsonFormat(pattern = "yyyy/mm/dd")
    private LocalDate expiryDate;

}
