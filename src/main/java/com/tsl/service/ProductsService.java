package com.tsl.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tsl.help.Helper;
import com.tsl.model.Products;
import com.tsl.repository.ProductsRepo;

@Service
public class ProductsService {

    private static final Logger logger = LoggerFactory.getLogger(ProductsService.class);

    @Autowired
    private ProductsRepo repo;

    public void save(MultipartFile file) {
        try {
            List<Products> productList = Helper.convertExcelToListOfProduct(file.getInputStream(), file.getContentType());

            if (productList == null || productList.isEmpty()) {
                throw new RuntimeException("Failed to convert Excel to product list. List is null or empty.");
            }

            for (Products product : productList) {
                if (product == null) {
                    throw new RuntimeException("Product entity is null. Conversion failed.");
                }
            }

            this.repo.saveAll(productList);

        } catch (IOException e) {
            logger.error("Error processing the file", e);
            throw new RuntimeException("Error processing the file: " + e.getMessage(), e);
        }
    }

    public List<Products> getAllProducts() {
        return this.repo.findAll();
    }
}
