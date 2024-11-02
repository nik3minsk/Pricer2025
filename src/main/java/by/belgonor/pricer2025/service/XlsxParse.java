package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.controller.FileController;
import by.belgonor.pricer2025.entity.RulesForXlsx;
import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.entity.XlsxHeaderValue;
import by.belgonor.pricer2025.repository.RulesForXlsxRepo;
import by.belgonor.pricer2025.repository.XlsxHeaderValueRepo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class XlsxParse {

    @Autowired
    private  RulesForXlsxRepo rulesForXlsxRepo;

    @Autowired
    private XlsxHeaderValueRepo xlsxHeaderValueRepo;

    public String checkFailInHeader(Seller seller) {
        System.out.println(" \b ===  checkFailInHeader======= \b");
        String failCollumnsNumbers = "";
        System.out.println("\bseller = \b" + seller);
        System.out.println("seller.getXlsPriceRules() = " + seller.getXlsPriceRules());



        RulesForXlsx salerXlsxRules = rulesForXlsxRepo.findById(seller.getXlsPriceRules().getId()).orElse(null);
//        RulesForXlsx salerXlsxRules = rulesForXlsxRepo.findById(seller.getId()).orElse(null);
        System.out.println(" ===================== ");
        System.out.println("\bsalerXlsxRules = \b" + salerXlsxRules);

        Optional<XlsxHeaderValue> validHeaderValues = xlsxHeaderValueRepo.findById(seller.getXlsPriceRules().getHeaderValues().getId());
        System.out.println(" ===================== ");
        System.out.println("validHeaderValues = " + validHeaderValues);
//        System.out.println("\bxlsxHeaderValueRepo.findById(salerXlsxRules.getId()) = \b" + validHeaderValues);
//        System.out.println("\bsalerXlsxRules.getId() = \b" + salerXlsxRules.);

//        RulesForXlsx newRules = rulesForXlsxRepo.findById(seller.getId());


        XlsxHeaderValue newHeaderValues = new XlsxHeaderValue();
        parseXlsxHeader(seller.getPathToPrice(), seller.getXlsPriceRules().getHeaderStringNumber(), salerXlsxRules, newHeaderValues);
        System.out.println("\bnewHeaderValues = \b" + newHeaderValues);

        // Вызов метода для сравнения полей
        String result = compareXlsxHeaderValues(newHeaderValues, validHeaderValues.orElse(null));
        if (!result.isEmpty()) {
            failCollumnsNumbers = result;
        }
//          надо избавиться от лишней переменной
        return failCollumnsNumbers;
    }


    public String compareXlsxHeaderValues(XlsxHeaderValue newHeaderValues, XlsxHeaderValue validHeaderValues) {
        if (validHeaderValues == null) {
            System.out.println("Valid header values are null.");
            return "All valid header values are null.";
        }

        StringBuilder mismatchFields = new StringBuilder();

        if (!equalsOrNull(newHeaderValues.getColumnBrand(), validHeaderValues.getColumnBrand())) {
            mismatchFields.append("columnBrand ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnArticle(), validHeaderValues.getColumnArticle())) {
            mismatchFields.append("columnArticle ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnProductCategory(), validHeaderValues.getColumnProductCategory())) {
            mismatchFields.append("columnProductCategory ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnPrice(), validHeaderValues.getColumnPrice())) {
            mismatchFields.append("columnPrice ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnOnStock(), validHeaderValues.getColumnOnStock())) {
            mismatchFields.append("columnOnStock ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnTnved(), validHeaderValues.getColumnTnved())) {
            mismatchFields.append("columnTnved ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnBarcode(), validHeaderValues.getColumnBarcode())) {
            mismatchFields.append("columnBarcode ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnPriceOnStockOwn(), validHeaderValues.getColumnPriceOnStockOwn())) {
            mismatchFields.append("columnPriceOnStockOwn ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnOnStockOwn(), validHeaderValues.getColumnOnStockOwn())) {
            mismatchFields.append("columnOnStockOwn ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnReservedOnStockOwn(), validHeaderValues.getColumnReservedOnStockOwn())) {
            mismatchFields.append("columnReservedOnStockOwn ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnFreeOnStock(), validHeaderValues.getColumnFreeOnStock())) {
            mismatchFields.append("columnFreeOnStock ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnPriceForSiteOwn(), validHeaderValues.getColumnPriceForSiteOwn())) {
            mismatchFields.append("columnPriceForSiteOwn ");
        }
        if (!equalsOrNull(newHeaderValues.getColumnProductName(), validHeaderValues.getColumnProductName())) {
            mismatchFields.append("columnProductName ");
        }

        if (mismatchFields.length() == 0) {
            System.out.println("Header values match.");
            return "";
        } else {
            System.out.println("Header values do not match. Mismatched fields: " + mismatchFields.toString().trim());
            return mismatchFields.toString().trim();
        }
    }

    private boolean equalsOrNull(String s1, String s2) {
        return (s1 == null && s2 == null) || (s1 != null && s1.equals(s2));
    }


//    public boolean compareXlsxHeaderValues(XlsxHeaderValue newHeaderValues, XlsxHeaderValue validHeaderValues) {
//        if (validHeaderValues == null) {
//            System.out.println("Valid header values are null.");
//            return false;
//        }
//
//        boolean result = true;
//
//        result = result && (newHeaderValues.getColumnBrand() == null ? validHeaderValues.getColumnBrand() == null : newHeaderValues.getColumnBrand().equals(validHeaderValues.getColumnBrand()));
//        result = result && (newHeaderValues.getColumnArticle() == null ? validHeaderValues.getColumnArticle() == null : newHeaderValues.getColumnArticle().equals(validHeaderValues.getColumnArticle()));
//        result = result && (newHeaderValues.getColumnProductCategory() == null ? validHeaderValues.getColumnProductCategory() == null : newHeaderValues.getColumnProductCategory().equals(validHeaderValues.getColumnProductCategory()));
//        result = result && (newHeaderValues.getColumnPrice() == null ? validHeaderValues.getColumnPrice() == null : newHeaderValues.getColumnPrice().equals(validHeaderValues.getColumnPrice()));
//        result = result && (newHeaderValues.getColumnOnStock() == null ? validHeaderValues.getColumnOnStock() == null : newHeaderValues.getColumnOnStock().equals(validHeaderValues.getColumnOnStock()));
//        result = result && (newHeaderValues.getColumnTnved() == null ? validHeaderValues.getColumnTnved() == null : newHeaderValues.getColumnTnved().equals(validHeaderValues.getColumnTnved()));
//        result = result && (newHeaderValues.getColumnBarcode() == null ? validHeaderValues.getColumnBarcode() == null : newHeaderValues.getColumnBarcode().equals(validHeaderValues.getColumnBarcode()));
//        result = result && (newHeaderValues.getColumnPriceOnStockOwn() == null ? validHeaderValues.getColumnPriceOnStockOwn() == null : newHeaderValues.getColumnPriceOnStockOwn().equals(validHeaderValues.getColumnPriceOnStockOwn()));
//        result = result && (newHeaderValues.getColumnOnStockOwn() == null ? validHeaderValues.getColumnOnStockOwn() == null : newHeaderValues.getColumnOnStockOwn().equals(validHeaderValues.getColumnOnStockOwn()));
//        result = result && (newHeaderValues.getColumnReservedOnStockOwn() == null ? validHeaderValues.getColumnReservedOnStockOwn() == null : newHeaderValues.getColumnReservedOnStockOwn().equals(validHeaderValues.getColumnReservedOnStockOwn()));
//        result = result && (newHeaderValues.getColumnFreeOnStock() == null ? validHeaderValues.getColumnFreeOnStock() == null : newHeaderValues.getColumnFreeOnStock().equals(validHeaderValues.getColumnFreeOnStock()));
//        result = result && (newHeaderValues.getColumnPriceForSiteOwn() == null ? validHeaderValues.getColumnPriceForSiteOwn() == null : newHeaderValues.getColumnPriceForSiteOwn().equals(validHeaderValues.getColumnPriceForSiteOwn()));
//        result = result && (newHeaderValues.getColumnProductName() == null ? validHeaderValues.getColumnProductName() == null : newHeaderValues.getColumnProductName().equals(validHeaderValues.getColumnProductName()));
//
//        if (result) {
//            System.out.println("Header values match.");
//        } else {
//            System.out.println("Header values do not match.");
//        }
//
//        return result;
//    }



//                  ##########   парсинг значений шапки c записью их в объект XlsxHeaderValue headerValues ###################
    public static void parseXlsxHeader(String fileToRead, Integer headerStringNumber, RulesForXlsx rulesForXlsxHeaders, XlsxHeaderValue headerValues){
        Path.of(fileToRead);
        System.out.println("pathOfFile = " + fileToRead);

        InputStream inputStream = null;
        XSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream(fileToRead.toString());
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workBook.getSheetAt(0);

        int rowStart = headerStringNumber - 1;
        int rowEnd = headerStringNumber - 1;

        System.out.println("headerStringNumber = " + headerStringNumber);
        System.out.println("rowStart = " + rowStart);

        for (int rw = rowStart; rw <= rowEnd; rw++) {
            XSSFRow row = sheet.getRow(rw);
            if (row == null) {
                System.out.println("row '" + rw + "' is not created");
                continue;
            }
//            записываем значения полей шапки в объект headerValues

            if (rulesForXlsxHeaders.getColumnBrand() - 1 >= 0) {
                headerValues.setColumnBrand(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnBrand() - 1).toString());
            }

            System.out.println("по ячейке = " + row.getCell(0).getStringCellValue());

            if (rulesForXlsxHeaders.getColumnArticle() - 1 >= 0) {
                headerValues.setColumnArticle(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnArticle() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnProductCategory() - 1 >= 0) {
                headerValues.setColumnProductCategory(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnProductCategory() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnPrice() - 1 >= 0) {
                headerValues.setColumnPrice(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnPrice() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnOnStock() - 1 >= 0) {
                headerValues.setColumnOnStock(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnOnStock() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnTnved() - 1 >= 0) {
                headerValues.setColumnTnved(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnTnved() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnBarcode() - 1 >= 0) {
                headerValues.setColumnBarcode(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnBarcode() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnPriceOnStockOwn() - 1 >= 0) {
                headerValues.setColumnPriceOnStockOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnPriceOnStockOwn() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnOnStockOwn() - 1 >= 0) {
                headerValues.setColumnOnStockOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnOnStockOwn() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnReservedOnStockOwn() - 1 >= 0) {
                headerValues.setColumnReservedOnStockOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnReservedOnStockOwn() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnFreeOnStock() - 1 >= 0) {
                headerValues.setColumnFreeOnStock(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnFreeOnStock() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnPriceForSiteOwn() - 1 >= 0) {
                headerValues.setColumnPriceForSiteOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnPriceForSiteOwn() - 1).toString());
            }

            if (rulesForXlsxHeaders.getColumnProductName() - 1 >= 0) {
                headerValues.setColumnProductName(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnProductName() - 1).toString());
            }
//            System.out.println("headerValues = " + headerValues);
        }
    }


}
