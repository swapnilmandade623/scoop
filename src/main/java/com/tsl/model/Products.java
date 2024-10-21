package com.tsl.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Company_Details")
public class Products{
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer cid;
 private String Companyname;
 private String industryid;	
 private String addressline1;	
 private String stateid;	
 private String  cityid;
 private String pincode;
 private String phoneno1;
 @Column(unique = true)
 private String website;	
 private String turnoverrange;
 private String employeerange;
 private String firstname;	
 private String lastname;	
 private String jobtitle;	
 private String emailid;

}
