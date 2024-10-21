package com.tsl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsl.model.Products;
import com.tsl.repository.ProductsRepo;
import com.tsl.util.DomainUtils;
@Service
public class ServiceProduct {
	@Autowired
	private ProductsRepo repo;
		public boolean saveProductIfNotDuplicate(String website,Products p) {
		    System.out.println("Provided URL: " + website); 
		    String baseDomain = DomainUtils.extractBaseDomain(website);

		    System.out.println("Extracted Base Domain: " + baseDomain); // Print extracted base domain

		    if (baseDomain == null) {
		        throw new RuntimeException("Invalid URL provided");
		    }

		    if (repo.existsBywebsite(baseDomain)) {
		        System.out.println("Duplicate website found: " + baseDomain); // Print duplicate message
		        return false; 
		    }

		    p.setWebsite(baseDomain);
		    repo.save(p);
		    
		    System.out.println("Invoice saved successfully for: " + baseDomain); // Print success message
		    return true; 
		}
}