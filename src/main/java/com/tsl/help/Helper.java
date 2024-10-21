package com.tsl.help;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.tsl.model.Products;

public class Helper {

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.ms-excel") || 
               contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private List<Products> createList(List<String> excelData, int noOfColumns) {
        ArrayList<Products> productList = new ArrayList<>();

        for (int i = noOfColumns; i < excelData.size(); i += noOfColumns) {
            Products product = new Products();

            product.setCompanyname(excelData.get(i)); 
            product.setIndustryid(excelData.get(i + 1)); 
            product.setAddressline1(excelData.get(i + 2)); 
            product.setStateid(excelData.get(i + 3)); 
            product.setCityid(excelData.get(i + 4)); 
            product.setPincode(excelData.get(i + 5)); 
            product.setPhoneno1(excelData.get(i + 6)); 
            product.setWebsite(excelData.get(i + 7)); 
            product.setTurnoverrange(excelData.get(i + 8)); 
            product.setEmployeerange(excelData.get(i + 9)); 
            product.setFirstname(excelData.get(i + 10)); 
            product.setLastname(excelData.get(i + 11)); 
            product.setJobtitle(excelData.get(i + 12)); 
            product.setEmailid(excelData.get(i + 13)); 

            productList.add(product);
        }
        return productList;
    }
    public static List<Products> convertExcelToListOfProduct(InputStream is, String contentType) {
        List<Products> productList = new ArrayList<>();
        try {
            Workbook workbook = null;

            if (contentType.equals("application/vnd.ms-excel")) {
                workbook = new HSSFWorkbook(is); // For .xls
            } else if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                workbook = new XSSFWorkbook(is); // For .xlsx
            } else {
                throw new IllegalArgumentException("Invalid file type. Only Excel files are supported.");
            }

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            if (sheet == null) {
                throw new RuntimeException("No sheet found in the Excel file.");
            }

            Iterator<Row> rows = sheet.iterator();
            List<String> excelData = new ArrayList<>();

            int rowNumber = 0;
            int noOfColumns = 14; // 14 fields expected in Products entity

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (currentCell.getCellType()) {
                        case STRING:
                            excelData.add(currentCell.getStringCellValue());
                            break;
                        case NUMERIC:
                            excelData.add(String.valueOf((long) currentCell.getNumericCellValue()));
                            break;
                        default:
                            excelData.add(""); // Handle empty cells
                            break;
                    }
                }
                rowNumber++;
            }

            // Log the size of excelData
            if (excelData.isEmpty()) {
                throw new RuntimeException("Excel data is empty.");
            }
           
            // Convert the extracted data to Products list
            productList = new Helper().createList(excelData, noOfColumns);

            if (productList.isEmpty()) {
                throw new RuntimeException("No products created from the Excel data.");
            }

            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        }

        return productList;
    }
}