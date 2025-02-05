package com.example.swiftcodes.service;

import com.example.swiftcodes.model.SwiftCode;
import com.example.swiftcodes.repository.SwiftCodeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class SwiftCodeParser {

    @Autowired
    protected SwiftCodeRepository swiftCodeRepository;

    public void parseExcelFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            String swiftCode = row.getCell(1).getStringCellValue();
            String bankName = row.getCell(3).getStringCellValue();
            String address = row.getCell(4).getStringCellValue();
            String countryISO2 = row.getCell(0).getStringCellValue().toUpperCase();
            String countryName = row.getCell(6).getStringCellValue().toUpperCase();
            boolean isHeadquarter = swiftCode.endsWith("XXX");

            SwiftCode existingSwiftCode = swiftCodeRepository.findBySwiftCode(swiftCode);
            if (existingSwiftCode != null) {
                existingSwiftCode.setBankName(bankName);
                existingSwiftCode.setAddress(address);
                existingSwiftCode.setCountryISO2(countryISO2);
                existingSwiftCode.setCountryName(countryName);
                existingSwiftCode.setHeadquarter(isHeadquarter);
                swiftCodeRepository.save(existingSwiftCode);
            } else {
                SwiftCode swiftCodeObj = new SwiftCode(swiftCode, bankName, address, countryISO2, countryName, isHeadquarter);
                swiftCodeRepository.save(swiftCodeObj);
            }
        }

        workbook.close();
        fis.close();
    }
}
