package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotEmpty(message = "the category ID cant be empty ")
    private String id;
    @NotEmpty(message = "category name cant be empty")
    @Size(min = 4,message = "the category name cant be less than 4")
    private String name;
}
