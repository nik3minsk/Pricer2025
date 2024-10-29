package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.RulesForXlsx;
import by.belgonor.pricer2025.entity.XlsxHeaderValue;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;


public class XlsxParse {

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

            if (rulesForXlsxHeaders.getColumnBrand() - 1 > 0) {
                headerValues.setColumnBrand(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnBrand() - 1).toString());
                System.out.println("1 headerValues.getColumnBrand() = " + headerValues.getColumnBrand());
            }

            if (rulesForXlsxHeaders.getColumnArticle() - 1 > 0) {
                headerValues.setColumnArticle(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnArticle() - 1).toString());
                System.out.println("2 headerValues.getColumnArticle() = " + headerValues.getColumnArticle());
            }

            if (rulesForXlsxHeaders.getColumnProductCategory() - 1 > 0) {
                headerValues.setColumnProductCategory(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnProductCategory() - 1).toString());
                System.out.println("3 headerValues.getColumnProductCategory() = " + headerValues.getColumnProductCategory());
            }

            if (rulesForXlsxHeaders.getColumnPrice() - 1 > 0) {
                headerValues.setColumnPrice(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnPrice() - 1).toString());
                System.out.println("4 headerValues.getColumnPrice() = " + headerValues.getColumnPrice());
            }

            if (rulesForXlsxHeaders.getColumnOnStock() - 1 > 0) {
                headerValues.setColumnOnStock(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnOnStock() - 1).toString());
                System.out.println("5 headerValues.getColumnOnStock() = " + headerValues.getColumnOnStock());
            }

            if (rulesForXlsxHeaders.getColumnTnved() - 1 > 0) {
                headerValues.setColumnTnved(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnTnved() - 1).toString());
                System.out.println("6 headerValues.getColumnTnved() = " + headerValues.getColumnTnved());
            }

            if (rulesForXlsxHeaders.getColumnBarcode() - 1 > 0) {
                headerValues.setColumnBarcode(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnBarcode() - 1).toString());
                System.out.println("7 headerValues.getColumnBarcode() = " + headerValues.getColumnBarcode());
            }

            if (rulesForXlsxHeaders.getColumnOnStockOwn() - 1 > 0) {
                headerValues.setColumnOnStockOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnOnStockOwn() - 1).toString());
                System.out.println("8 headerValues.getColumnOnStockOwn() = " + headerValues.getColumnOnStockOwn());
            }

            if (rulesForXlsxHeaders.getColumnReservedOnStockOwn() - 1 > 0) {
                headerValues.setColumnReservedOnStockOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnReservedOnStockOwn() - 1).toString());
                System.out.println("9 headerValues.getColumnReservedOnStockOwn() = " + headerValues.getColumnReservedOnStockOwn());
            }

            if (rulesForXlsxHeaders.getColumnFreeOnStock() - 1 > 0) {
                headerValues.setColumnFreeOnStock(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnFreeOnStock() - 1).toString());
                System.out.println("10 headerValues.getColumnFreeOnStock() = " + headerValues.getColumnFreeOnStock());
            }

            if (rulesForXlsxHeaders.getColumnPriceForSiteOwn() - 1 > 0) {
                headerValues.setColumnPriceForSiteOwn(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnPriceForSiteOwn() - 1).toString());
                System.out.println("11 headerValues.getColumnPriceForSiteOwn() = " + headerValues.getColumnPriceForSiteOwn());
            }

            if (rulesForXlsxHeaders.getColumnProductName() - 1 > 0) {
                headerValues.setColumnProductName(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnProductName() - 1).toString());
                System.out.println("12 headerValues.getColumnProductName() = " + headerValues.getColumnProductName());
            }
//            System.out.println("headerValues = " + headerValues);
        }
    }
}
