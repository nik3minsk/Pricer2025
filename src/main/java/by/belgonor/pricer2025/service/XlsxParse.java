package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.controller.FileController;
import by.belgonor.pricer2025.entity.*;
import by.belgonor.pricer2025.repository.BrandRepo;
import by.belgonor.pricer2025.repository.RulesForXlsxRepo;
import by.belgonor.pricer2025.repository.TotalPriceRepo;
import by.belgonor.pricer2025.repository.XlsxHeaderValueRepo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class XlsxParse {

    @Autowired
    private  RulesForXlsxRepo rulesForXlsxRepo;

    @Autowired
    private XlsxHeaderValueRepo xlsxHeaderValueRepo;
    @Autowired
    private View error;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private BrandService brandService;

    @Autowired
    private TotalPriceRepo totalPriceRepo;


    public String checkFailInAllSellersHeaders(List<Seller> sellers) {
        StringBuilder errorText = new StringBuilder();
        System.out.println(" ================   мы в   checkFailInAllSellersHeaders =====  ");
        for (Seller seller: sellers){
            System.out.println("seller = " + seller);
            String result = checkFailInHeader(seller);
            if (!result.isEmpty()) {
                if (errorText.length() > 0) {
                    errorText.append(" ");
                }
                errorText.append("Прайс ").append(seller.getPriceName()).append(" содержит ошибки в столбцах: ").append(result);
            }
        }
        return errorText.toString();
    }

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

        XlsxHeaderValue newHeaderValues = new XlsxHeaderValue();
        parseXlsxHeader(seller.getPathToPrice(), seller.getXlsPriceRules().getHeaderStringNumber(), salerXlsxRules, newHeaderValues);
        System.out.println("\bnewHeaderValues = \b" + newHeaderValues);

        // Вызов метода для сравнения полей
        failCollumnsNumbers = compareXlsxHeaderValues(newHeaderValues, validHeaderValues.orElse(null));
//        System.out.println("newHeaderValues = " + failCollumnsNumbers);

        return failCollumnsNumbers;
    }


    public String compareXlsxHeaderValues(XlsxHeaderValue newHeaderValues, XlsxHeaderValue validHeaderValues) {
        if (validHeaderValues == null) {
            System.out.println("Valid header values are null.");
            return "All valid header values are null.";
        }

        StringBuilder mismatchFields = new StringBuilder();

        if (!equalsOrNull(newHeaderValues.getColumnBrand(), validHeaderValues.getColumnBrand())) {
            addMismatchField(mismatchFields, "columnBrand");
        }
        if (!equalsOrNull(newHeaderValues.getColumnArticle(), validHeaderValues.getColumnArticle())) {
            addMismatchField(mismatchFields, "columnArticle");
        }
        if (!equalsOrNull(newHeaderValues.getColumnProductCategory(), validHeaderValues.getColumnProductCategory())) {
            addMismatchField(mismatchFields, "columnProductCategory");
        }
        if (!equalsOrNull(newHeaderValues.getColumnPrice(), validHeaderValues.getColumnPrice())) {
            addMismatchField(mismatchFields, "columnPrice");
        }
        if (!equalsOrNull(newHeaderValues.getColumnOnStock(), validHeaderValues.getColumnOnStock())) {
            addMismatchField(mismatchFields, "columnOnStock");
        }
        if (!equalsOrNull(newHeaderValues.getColumnTnved(), validHeaderValues.getColumnTnved())) {
            addMismatchField(mismatchFields, "columnTnved");
        }
        if (!equalsOrNull(newHeaderValues.getColumnBarcode(), validHeaderValues.getColumnBarcode())) {
            addMismatchField(mismatchFields, "columnBarcode");
        }
        if (!equalsOrNull(newHeaderValues.getColumnPriceOnStockOwn(), validHeaderValues.getColumnPriceOnStockOwn())) {
            addMismatchField(mismatchFields, "columnPriceOnStockOwn");
        }
        if (!equalsOrNull(newHeaderValues.getColumnOnStockOwn(), validHeaderValues.getColumnOnStockOwn())) {
            addMismatchField(mismatchFields, "columnOnStockOwn");
        }
        if (!equalsOrNull(newHeaderValues.getColumnReservedOnStockOwn(), validHeaderValues.getColumnReservedOnStockOwn())) {
            addMismatchField(mismatchFields, "columnReservedOnStockOwn");
        }
        if (!equalsOrNull(newHeaderValues.getColumnFreeOnStock(), validHeaderValues.getColumnFreeOnStock())) {
            addMismatchField(mismatchFields, "columnFreeOnStock");
        }
        if (!equalsOrNull(newHeaderValues.getColumnPriceForSiteOwn(), validHeaderValues.getColumnPriceForSiteOwn())) {
            addMismatchField(mismatchFields, "columnPriceForSiteOwn");
        }
        if (!equalsOrNull(newHeaderValues.getColumnProductName(), validHeaderValues.getColumnProductName())) {
            addMismatchField(mismatchFields, "columnProductName");
        }

        if (mismatchFields.length() == 0) {
            System.out.println("Header values match.");
            return "";
        } else {
            System.out.println("Header values do not match. Mismatched fields: " + mismatchFields.toString().trim() + ".");
            return mismatchFields.toString().trim() + ".";
        }
    }

    private void addMismatchField(StringBuilder mismatchFields, String fieldName) {
        if (mismatchFields.length() > 0) {
            mismatchFields.append(", ");
        }
        mismatchFields.append(fieldName);
    }

    private boolean equalsOrNull(String s1, String s2) {
        return (s1 == null && s2 == null) || (s1 != null && s1.equals(s2));
    }


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

//    парсинг всех файлов поставщиков

    public String parseAllXlsxFiles (List<Seller> sellers){
        StringBuilder errorText = new StringBuilder();
        System.out.println(" ================   мы в   parseAllXlsxFiles =====  ");
        for (Seller seller: sellers){
            System.out.println("seller = " + seller);
            String result = parseXlsxFile(seller);
            if (!result.isEmpty()) {
                if (errorText.length() > 0) {
                    errorText.append(" ");
                }
                errorText.append("Прайс ").append(seller.getPriceName()).append(" содержит ошибки");
            }
        }
        return errorText.toString();
    }

    public String parseXlsxFile(Seller seller){
        String badParsingInFile = seller.getPriceName();
        String fileToRead = seller.getPathToPrice();
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

        int rowStart = seller.getXlsPriceRules().getStartPriceDataRowNumber() - 1;
        int rowEnd = sheet.getLastRowNum();
        TotalPrice addToPrice = new TotalPrice();
        List<Brand> brands = brandService.findAll();
        System.out.println("brands = " + brands);


        for (int rw = rowStart; rw <= rowEnd; rw++) {
            XSSFRow row = sheet.getRow(rw);
            if (row == null) {
                System.out.println("row '" + rw + "' is not created");
                continue;
            }
//            записываем значения полей шапки в объект headerValues

//            if (rulesForXlsxHeaders.getColumnBrand() - 1 >= 0) {
//                headerValues.setColumnBrand(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnBrand() - 1).toString());
//            }
//
//            System.out.println("по ячейке = " + row.getCell(0).getStringCellValue());
//
//            if (rulesForXlsxHeaders.getColumnArticle() - 1 >= 0) {
//                headerValues.setColumnArticle(FindXlsxCellsFormat.cellOfString(row, rulesForXlsxHeaders.getColumnArticle() - 1).toString());
//            }


//            System.out.println("headerValues = " + headerValues);
        }

        return badParsingInFile;
    }
}
