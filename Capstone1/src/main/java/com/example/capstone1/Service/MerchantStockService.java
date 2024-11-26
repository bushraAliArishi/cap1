package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;

import java.util.ArrayList;

public class MerchantStockService {
    final private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    final private ProductService productService = new ProductService();
    final private MerchantService merchantService = new MerchantService();

    // CRUD
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public String addMerchantStock(MerchantStock merchantStock, String merchantID, String productID) {
        boolean productExists = false;
        for (Product product : productService.getProducts()) {
            if (product.getId().equalsIgnoreCase(productID)) {
                productExists = true;
                break;
            }
        }

        boolean merchantExists = false;
        for (Merchant merchant : merchantService.getMerchants()) {
            if (merchant.getId().equalsIgnoreCase(merchantID)) {
                merchantExists = true;
                break;
            }
        }

        if (!productExists) {
            return "Invalid product ID.";
        }
        if (!merchantExists) {
            return "Invalid merchant ID.";
        }

        merchantStocks.add(merchantStock);
        return "Merchant stock added successfully.";
    }

    public boolean updateMerchantStock(String id, MerchantStock updatedStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.set(i, updatedStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean increaseStock(String merchantID, String productID, int quantity) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID) &&
                    stock.getProductID().equalsIgnoreCase(productID)) {
                stock.setStock(stock.getStock() + quantity);
                return true;
            }
        }
        return false;
    }

    public boolean decreaseStock(String merchantID, String productID, int quantity) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID) &&
                    stock.getProductID().equalsIgnoreCase(productID)) {
                if (stock.getStock() >= quantity) {
                    stock.setStock(stock.getStock() - quantity);
                    return true;
                }
                return false; // Insufficient stock
            }
        }
        return false;
    }

    public MerchantStock findMerchantStock(String merchantID, String productID) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID) &&
                    stock.getProductID().equalsIgnoreCase(productID)) {
                return stock;
            }
        }
        return null;
    }

    public ArrayList<MerchantStock> findLowStock(int threshold) {
        ArrayList<MerchantStock> lowStock = new ArrayList<>();
        for (MerchantStock stock : merchantStocks) {
            if (stock.getStock() < threshold) {
                lowStock.add(stock);
            }
        }
        return lowStock;
    }

    public ArrayList<MerchantStock> findMerchantStocksByMerchant(String merchantID) {
        ArrayList<MerchantStock> merchantStocksList = new ArrayList<>();
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID)) {
                merchantStocksList.add(stock);
            }
        }
        return merchantStocksList;
    }

    public int countProductsForMerchant(String merchantID) {
        int count = 0;
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID)) {
                count++;
            }
        }
        return count;
    }
}
