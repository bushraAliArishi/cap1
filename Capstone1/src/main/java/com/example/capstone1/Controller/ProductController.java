package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")

public class ProductController {

    final private ProductService productService = new ProductService();

    @GetMapping("/get")
    public ResponseEntity getProducts() {
        ArrayList<Product> products = productService.getProducts();
        return ResponseEntity.status(200).body(new ApiResponse("Products retrieved successfully."+ products));
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String response = productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean updated = productService.updateProduct(id, product);
        if (updated) {
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found."));
    }

    @GetMapping("/category/{categoryID}")
    public ResponseEntity getProductsByCategory(@PathVariable String categoryID) {
        ArrayList<Product> products = productService.getProductsByCategory(categoryID);
        return ResponseEntity.status(200).body(new ApiResponse("Products in category " + categoryID+" "+ products));
    }

    @GetMapping("/cheapest")
    public ResponseEntity findCheapestProduct() {
        Product cheapest = productService.findCheapestProduct();
        if (cheapest != null) {
            return ResponseEntity.status(200).body(new ApiResponse("Cheapest product found."+ cheapest));
        }
        return ResponseEntity.status(400).body(new ApiResponse("No products available."));
    }

    @GetMapping("/count/{categoryID}")
    public ResponseEntity countProductsByCategory(@PathVariable String categoryID) {
        int count = productService.countProductsByCategory(categoryID);
        return ResponseEntity.status(200).body(new ApiResponse("Total products in category " + categoryID + ": " + count));
    }

    @PutMapping("/{id}/discount/{percentage}")
    public ResponseEntity applyDiscountToProduct(@PathVariable String id, @PathVariable double percentage) {
        boolean success = productService.applyDiscountToProduct(id, percentage);
        if (success) {
            return ResponseEntity.status(200).body(new ApiResponse("Discount applied successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found."));
    }
    @PutMapping("/setExpiryDate/{id}/{expiryDate}")
    public ResponseEntity setProductExpiryDate(@PathVariable String id, @PathVariable LocalDate expiryDate) {
        String response = productService.setProductExpiryDate(id, expiryDate);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }


}
