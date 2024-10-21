package com.tsl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tsl.model.Products;
import com.tsl.service.ProductsService;
import com.tsl.service.ServiceProduct;

@RestController
@CrossOrigin("http://localhost:3000/")
public class ExcelController {

    @Autowired
    private ProductsService service;
   @Autowired
    private ServiceProduct sp;
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
     this.service.save(file);
   return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved."));
       
}
    @GetMapping("/products")
    public List<Products> getAllProducts() {
        return this.service.getAllProducts();
    }
    @PostMapping("/check")
    public ResponseEntity<String> saveProduct(@RequestBody Products p) {
        String website = p.getWebsite(); 
        boolean isSaved = sp.saveProductIfNotDuplicate(website, p);

        if (isSaved) {
            return ResponseEntity.ok("Invoice saved successfully.");
        } else {
            return ResponseEntity.badRequest().body("Duplicate domain found, invoice not saved.");
        }
    }
   }

