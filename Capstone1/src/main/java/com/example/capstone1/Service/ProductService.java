package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductService {
    final private ArrayList<Product> products = new ArrayList<>();

    // CRUD Operations
    public ArrayList<Product> getProducts() {
        return products;
    }

    public String addProduct(Product product) {
        products.add(product);
        return "Product added successfully.";
    }

    public boolean updateProduct(String id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    // Additional Endpoints
    public ArrayList<Product> getProductsByCategory(String categoryID) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategoryID().equalsIgnoreCase(categoryID)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public Product findCheapestProduct() {
        if (products.isEmpty()) return null;
        Product cheapest = products.get(0);
        for (Product product : products) {
            if (product.getPrice() < cheapest.getPrice()) {
                cheapest = product;
            }
        }
        return cheapest;
    }

    public int countProductsByCategory(String categoryID) {
        int count = 0;
        for (Product product : products) {
            if (product.getCategoryID().equalsIgnoreCase(categoryID)) {
                count++;
            }
        }
        return count;
    }

    public boolean applyDiscountToProduct(String id, double discountPercentage) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) {
                double discountedPrice = product.getPrice() - (product.getPrice() * discountPercentage / 100);
                product.setPrice(discountedPrice);
                return true;
            }
        }
        return false;
    }
    public String setProductExpiryDate(String id, String expiryDate) {

        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setExpiryDate(LocalDate.parse(expiryDate));
                return "Product expiry date updated.";
            }
        }
        return "Product not found.";
    }
}
