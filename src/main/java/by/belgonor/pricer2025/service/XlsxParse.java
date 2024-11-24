package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.*;
import by.belgonor.pricer2025.repository.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;


@Service
public class XlsxParse {

    @Autowired
    private RulesForXlsxRepo rulesForXlsxRepo;

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

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private CurrencyRateRepo currencyRateRepo;

    //        Проверяет наличие ошибок в шапке каждого из продавцов
    public String checkFailInAllSellersHeaders(List<Seller> sellers) {
        StringBuilder errorText = new StringBuilder();
        System.out.println(" ================   мы в   checkFailInAllSellersHeaders =====  ");
        for (Seller seller : sellers) {
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

    //        Проверяет наличие ошибок в шапке
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
    public static void parseXlsxHeader(String fileToRead, Integer headerStringNumber, RulesForXlsx rulesForXlsxHeaders, XlsxHeaderValue headerValues) {
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

    public String parseAllXlsxFiles(List<Seller> sellers) {
        StringBuilder errorText = new StringBuilder();
        System.out.println(" ================   мы в   parseAllXlsxFiles =====  ");
        for (Seller seller : sellers) {
            System.out.println("seller = " + seller);
            String result = parseXlsxFile(seller);
            System.out.println("result = " + result);
            if (!result.isEmpty()) {
                if (errorText.length() > 0) {
                    errorText.append(" ");
//                    errorText.append("Прайс ").append(seller.getPriceName()).append(" содержит ошибки");
                }
                errorText.append("Прайс ").append(seller.getPriceName()).append(" содержит ошибки");
            }
        }
        return errorText.toString();
    }

    public String parseXlsxFile(Seller seller) {
        String badParsingInFile = "";
        String fileToRead = seller.getPathToPrice();
        Path.of(fileToRead);
        System.out.println("start parsing of file where pathOfFile = " + fileToRead);
        LocalDate dateNow = LocalDate.now();

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
//        TotalPrice addToPrice = new TotalPrice();
//        ========= получаем значения всех брендов   =======
        List<Brand> brands = brandService.findAll();
        System.out.println("brands = " + brands);


        System.out.println("seller = " + seller.getPriceName());


        for (int rw = rowStart; rw <= rowEnd; rw++) {
            TotalPrice addToPrice = new TotalPrice();
            XSSFRow row = sheet.getRow(rw);
            if (row == null) {
                System.out.println("row '" + rw + "' is not created");
                continue;
            }


            Cell priceCell = row.getCell(seller.getXlsPriceRules().getColumnPrice() - 1);
            Cell brandCell = row.getCell(seller.getXlsPriceRules().getColumnBrand() - 1);
            Cell articleCell = row.getCell(seller.getXlsPriceRules().getColumnArticle() - 1);
            String brandCellValue = FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnBrand() - 1).trim();
            String articleCellValue = FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnArticle() - 1).trim();


            if ((brandCell != null && !brandCellValue.equals(""))
                    && (articleCell != null && !articleCellValue.equals(""))
//                    && priceCell != null && !priceCell.getStringCellValue().isEmpty()
            ) {

                // Продовжаем выполнение, если обе ячейки не равны null
                //     *   записываем поля первого ранга в промежуточный объект addToPrice
                addToPrice.setDate(dateNow);
                addToPrice.setIdSaler(seller);
                addToPrice.setCurrencyCode(seller.getEconomicRules().getCurrencyCode());
//      *      записываем значения полей в промежуточный объект addToPrice
//      *      проверяем наличие бренда в БД, если его нет - добавляем его
                boolean needNewBrand = true;
                Brand newBrand = null;
                for (Brand brand : brands) {
                    String brandFromXlsx = FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnBrand() - 1).trim();
//                    System.out.println("seller = " + seller);
//                    System.out.println("brandFromXlsx = " + brandFromXlsx);
                    if (brand.getBrandName().toString().toUpperCase().equals(brandFromXlsx.toUpperCase())) {
                        addToPrice.setIdBrand(brand);
                        needNewBrand = false;
                        break;
                    }
                }
                if (needNewBrand) {
                    newBrand = new Brand();
                    newBrand.setBrandName((FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnBrand() - 1).trim()).toUpperCase());
                    brandRepo.save(newBrand);
                    addToPrice.setIdBrand(newBrand);
                    brands = brandService.findAll();
                }


                //     *   записываем обязательные поля
                addToPrice.setArticle(FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnArticle() - 1).trim());
                addToPrice.setProductName(FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnProductName() - 1).trim());
                if (addToPrice.getProductName().length() > 300) {
                    addToPrice.setProductName(addToPrice.getProductName().substring(0, 300));
                }
                addToPrice.setPrice(FindXlsxCellsFormat.cellOfBigDecimal(row, seller.getXlsPriceRules().getColumnPrice() - 1));
                System.out.println("seller.getXlsPriceRules().getColumnPrice() = " + seller.getXlsPriceRules().getColumnPrice());
                System.out.println("addToPrice.getPrice() = " + addToPrice.getPrice());
//     *   записываем необязательные поля
                if (seller.getXlsPriceRules().getColumnProductCategory() != 0)
                    addToPrice.setProductCategory(FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnProductCategory() - 1).trim());
                if (seller.getXlsPriceRules().getColumnOnStock() != 0)
                    addToPrice.setOnStock(FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnOnStock() - 1).trim());
                if (seller.getXlsPriceRules().getColumnTnved() != 0)
                    addToPrice.setTnvedCode(FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnTnved() - 1).trim());
                if (seller.getXlsPriceRules().getColumnBarcode() != 0)
                    addToPrice.setBarcode(FindXlsxCellsFormat.cellOfString(row, seller.getXlsPriceRules().getColumnBarcode() - 1).trim());
                if (seller.getXlsPriceRules().getColumnPriceOnStockOwn() != 0)
                    addToPrice.setPriceOnStockOwn(FindXlsxCellsFormat.cellOfBigDecimal(row, seller.getXlsPriceRules().getColumnPriceOnStockOwn() - 1));
                if (seller.getXlsPriceRules().getColumnReservedOnStockOwn() != 0)
                    addToPrice.setReservationOnStock(FindXlsxCellsFormat.cellOfDouble(row, seller.getXlsPriceRules().getColumnReservedOnStockOwn() - 1));
                if (seller.getXlsPriceRules().getColumnFreeOnStock() != 0)
                    addToPrice.setFreeOnStock(FindXlsxCellsFormat.cellOfDouble(row, seller.getXlsPriceRules().getColumnFreeOnStock() - 1));
                if (seller.getXlsPriceRules().getColumnPriceForSiteOwn() != 0)
                    addToPrice.setPriceForSaleOwn(FindXlsxCellsFormat.cellOfBigDecimal(row, seller.getXlsPriceRules().getColumnPriceForSiteOwn() - 1));

//            *     запись в БД строки
                totalPriceRepo.save(addToPrice);
            }
        }
        return badParsingInFile;
    }

    public Path createXlsxImage() throws IOException {
        boolean isSuccess = false;

//        загружаем перечень продавцов
        List<Seller> arraySellers = new ArrayList<>();
        arraySellers = sellerRepo.findAll();
//        находим главного продавца (собственный прайс)
        Seller mainSeller = new Seller();
        for (Seller arraySeller : arraySellers) {
            if (arraySeller.getIsGeneralPrice()) mainSeller = arraySeller;
        }
//        System.out.println("mainSeller = " + mainSeller);
//        System.out.println("arraySellers = " + arraySellers);
//        System.out.println("arraySellers = " + arraySellers.size());

//        запрос данных из БД по собственному прайсу
        LocalDate dateNow = LocalDate.now();
        List<TotalPrice> sellerPriceFromDB;
        sellerPriceFromDB = totalPriceRepo.findByDateAndIdSaler(dateNow, mainSeller);
        System.out.println("arrayTotalPrice size = " + sellerPriceFromDB.size());
//        System.out.println("arrayTotalPrice = " + sellerPriceFromDB);


        ArrayList<FullPrice> fullPricesForXlsx = new ArrayList<>();


        for (TotalPrice sellerPrice : sellerPriceFromDB) {
            FullPrice addToPrice = new FullPrice();

            addToPrice.setBarcode(sellerPrice.getBarcode());
            addToPrice.setTnvedCode(sellerPrice.getTnvedCode());
            addToPrice.setBrand(sellerPrice.getIdBrand().getBrandName().toUpperCase());
            addToPrice.setArticle(sellerPrice.getArticle());
            addToPrice.setProductName(sellerPrice.getProductName());

            addToPrice.setOnStock(sellerPrice.getOnStock());
            addToPrice.setOnStockReserved(sellerPrice.getReservationOnStock().toString());
            addToPrice.setOnStockFree(sellerPrice.getFreeOnStock().toString());

            addToPrice.setRetailPrice(sellerPrice.getPriceForSaleOwn());
            addToPrice.setOurPrice(sellerPrice.getPrice());

            fullPricesForXlsx.add(addToPrice);
        }

        List<String> sellersNames = new ArrayList<>();
//        ДОБАВЛЕНИЕ ОСТАЛЬНЫХ ПРАЙСОВ (первый)
//      добавляем в массив значения цен и остатков по товарам от продавцов
        addSellersPrices(fullPricesForXlsx, arraySellers, sellersNames);

//        обнуление всех пустых ячеек в ценах
        for (FullPrice priceStr : fullPricesForXlsx) {
            if (priceStr.getOurPrice() == null) priceStr.setOurPrice(BigDecimal.ZERO);
            else {
                BigDecimal roundedPrice = priceStr.getOurPrice().setScale(2, RoundingMode.HALF_UP);
                priceStr.setOurPrice(roundedPrice);
            }
            if (priceStr.getRetailPrice() == null) priceStr.setRetailPrice(BigDecimal.ZERO);
            if (priceStr.getSellerPrice1() == null) priceStr.setSellerPrice1(BigDecimal.ZERO);
            if (priceStr.getSellerPrice2() == null) priceStr.setSellerPrice2(BigDecimal.ZERO);
            if (priceStr.getSellerPrice3() == null) priceStr.setSellerPrice3(BigDecimal.ZERO);
        }
//        вызываем метод для сохранения прайса на диск

//        FullPrice.saveXLSX(fullPricesForXlsx, sellersNames);

        return FullPrice.saveXLSX(fullPricesForXlsx, sellersNames);
    }

    public void roundPrice(ArrayList<FullPrice> fullPricesForXlsx) {
        //        обнуление всех пустых ячеек в ценах

        for (FullPrice priceStr : fullPricesForXlsx) {
            if (priceStr.getOurPrice() == null) priceStr.setOurPrice(BigDecimal.ZERO);
            else {
                BigDecimal roundedPrice = priceStr.getOurPrice().setScale(2, RoundingMode.HALF_UP);
                priceStr.setOurPrice(roundedPrice);
            }
            // Обработка значения RetailPrice
            if (priceStr.getRetailPrice() == null) {
                priceStr.setRetailPrice(BigDecimal.ZERO);
            } else {
                BigDecimal roundedRetailPrice = priceStr.getRetailPrice().setScale(2, RoundingMode.HALF_UP);
                priceStr.setRetailPrice(roundedRetailPrice);
            }
            // Обработка значения SellerPrice1
            if (priceStr.getSellerPrice1() == null) {
                priceStr.setSellerPrice1(BigDecimal.ZERO);
            } else {
                BigDecimal roundedSellerPrice1 = priceStr.getSellerPrice1().setScale(2, RoundingMode.HALF_UP);
                priceStr.setSellerPrice1(roundedSellerPrice1);
            }
            // Обработка значения SellerPrice2
            if (priceStr.getSellerPrice2() == null) {
                priceStr.setSellerPrice2(BigDecimal.ZERO);
            } else {
                BigDecimal roundedSellerPrice2 = priceStr.getSellerPrice2().setScale(2, RoundingMode.HALF_UP);
                priceStr.setSellerPrice2(roundedSellerPrice2);
            } // Обработка значения SellerPrice3
            if (priceStr.getSellerPrice3() == null) {
                priceStr.setSellerPrice3(BigDecimal.ZERO);
            } else {
                BigDecimal roundedSellerPrice3 = priceStr.getSellerPrice3().setScale(2, RoundingMode.HALF_UP);
                priceStr.setSellerPrice3(roundedSellerPrice3);
            }

        }

    }


    public void addSellersPrices(ArrayList<FullPrice> fullPricesForXlsx, List<Seller> arraySellers, List<String> sellersNames) {

        Seller seller1 = null;
        Seller seller2 = null;
        Seller seller3 = null;

        int assignedCount = 0;

        for (Seller seller : arraySellers) {
            if (!seller.getIsGeneralPrice() && seller.getId() != null) {
                switch (assignedCount) {
                    case 0:
                        seller1 = seller;
                        sellersNames.add(seller1.getPriceName());
                        break;
                    case 1:
                        seller2 = seller;
                        sellersNames.add(seller2.getPriceName());
                        break;
                    case 2:
                        seller3 = seller;
                        sellersNames.add(seller3.getPriceName());
                        break;
                    default:
                        System.out.println("Больше трех продавцов невозможно присвоить.");
                        break;
                }
                assignedCount++;
            }
        }

        // Выводим результаты для проверки
        System.out.println("Seller 1: " + (seller1 != null ? seller1 : "не назначен"));
        System.out.println("Seller 2: " + (seller2 != null ? seller2 : "не назначен"));
        System.out.println("Seller 3: " + (seller3 != null ? seller3 : "не назначен"));


        int size = arraySellers.size();

        switch (size) {
//            case 1:
//                System.out.println("Размер массива: 1. Обработка одного продавца.");
//                // Логика для одного продавца
////                handleOneSeller(arraySellers.get(0));
//                break;
            case 2:
                System.out.println("Размер массива: 2. Обработка одного продавца.");
                // Логика для двух продавцов
                addSeller1(fullPricesForXlsx, seller1);
//                handleTwoSellers(arraySellers.get(0), arraySellers.get(1));
                break;
            case 3:
                System.out.println("Размер массива: 3. Обработка двух продавцов.");
                addSeller1(fullPricesForXlsx, seller1);
                addSeller2(fullPricesForXlsx, seller2);
                // Логика для трех продавцов
//                handleThreeSellers(arraySellers.get(0), arraySellers.get(1), arraySellers.get(2));
                break;
            case 4:
                System.out.println("Размер массива: 3. Обработка трех продавцов.");
                // Логика для трех продавцов
                addSeller1(fullPricesForXlsx, seller1);
                addSeller2(fullPricesForXlsx, seller2);
                addSeller3(fullPricesForXlsx, seller3);
//                handleThreeSellers(arraySellers.get(0), arraySellers.get(1), arraySellers.get(2));
                break;
            default:
                System.out.println("Неподдерживаемый размер массива: " + size);
                // Логика для неподдерживаемого размера массива
//                handleUnsupportedSize(arraySellers);
                break;
        }


    }

    private void addSeller1(ArrayList<FullPrice> fullPricesForXlsx, Seller seller) {
        // Логика для одного продавца

//        for (FullPrice fullPrice : fullPricesForXlsx) {
//            System.out.println("FULL Price before = " + fullPrice);
//        }

        //        запрос данных из БД по собственному прайсу
        LocalDate dateNow = LocalDate.now();
        List<TotalPrice> sellerPriceFromDB;
        sellerPriceFromDB = totalPriceRepo.findByDateAndIdSaler(dateNow, seller);
        // Используем Set для удаления дубликатов
        Set<TotalPrice> uniqueSellerPricesSet = new HashSet<>(sellerPriceFromDB);
        // Преобразуем Set обратно в List
        List<TotalPrice> uniqueSellerPricesList = new ArrayList<>(uniqueSellerPricesSet);

        System.out.println("============= SELLER  1-1-1-1-1 ========  " + seller.getId());
        int tempCounter = 0;
        boolean needNewElement = true;
//        for (FullPrice fullPrice : fullPricesForXlsx) {
        for (TotalPrice sellerPrice : uniqueSellerPricesList) {
            FullPrice addToPrice = null;
//            for (TotalPrice sellerPrice : uniqueSellerPricesList) {
            for (FullPrice fullPrice : fullPricesForXlsx) {

//                System.out.println("fullPrice.getArticle().toUpperCase() = " + fullPrice.getArticle().toUpperCase());
//                System.out.println("sellerPrice.getArticle().toUpperCase() = " + sellerPrice.getArticle().toUpperCase());
//                System.out.println("fullPrice.getBrand().toUpperCase() = " + fullPrice.getBrand().toUpperCase());
//                System.out.println("sellerPrice.getIdBrand().getBrandName().toUpperCase() = " + sellerPrice.getIdBrand().getBrandName().toUpperCase());
//

//               элемент с брендом и артикулом найден, - добавляем цену и состояние склада
                if (fullPrice.getArticle().toUpperCase().equals(sellerPrice.getArticle().toUpperCase())
                        && fullPrice.getBrand().toUpperCase().equals(sellerPrice.getIdBrand().getBrandName().toUpperCase())) {
                    tempCounter++;
//                    System.out.println("tempCounter = " + tempCounter);

                    BigDecimal price = sellerPrice.getPrice();
                    price = calcRealPrice(seller, price);

                    fullPrice.setSellerPrice1(price);

                    fullPrice.setSellerStock1(sellerPrice.getOnStock());
                    System.out.println("добавляем цену = " + fullPrice);
                    needNewElement = false;
                    break;
                }
//               элемент с брендом и артикулом не найден, - заводим артикул, название добавляем цену и состояние склада

            }
            if (needNewElement == true) {

                addToPrice = new FullPrice();
                addToPrice.setBrand(sellerPrice.getIdBrand().getBrandName().toUpperCase());
                addToPrice.setArticle(sellerPrice.getArticle());
                addToPrice.setProductName(sellerPrice.getProductName());

                BigDecimal price = sellerPrice.getPrice();
                price = calcRealPrice(seller, price);
                addToPrice.setSellerPrice1(price);
                addToPrice.setSellerStock1(sellerPrice.getOnStock());

                fullPricesForXlsx.add(addToPrice);
//                System.out.println("Добавляем элемент позицию в прайс = " + addToPrice);
            }
            needNewElement = true;

        }
//        System.out.println(" ===================== " );
//        for (FullPrice fullPrice : fullPricesForXlsx) {
//            System.out.println("FULL Price after = " + fullPrice);
//        }
    }

    private void addSeller2(ArrayList<FullPrice> fullPricesForXlsx, Seller seller) {
        // Логика для одного продавца

//        for (FullPrice fullPrice : fullPricesForXlsx) {
//            System.out.println("FULL Price before = " + fullPrice);
//        }

        //        запрос данных из БД по собственному прайсу
        LocalDate dateNow = LocalDate.now();
        List<TotalPrice> sellerPriceFromDB;
        sellerPriceFromDB = totalPriceRepo.findByDateAndIdSaler(dateNow, seller);
        // Используем Set для удаления дубликатов
        Set<TotalPrice> uniqueSellerPricesSet = new HashSet<>(sellerPriceFromDB);
        // Преобразуем Set обратно в List
        List<TotalPrice> uniqueSellerPricesList = new ArrayList<>(uniqueSellerPricesSet);

        System.out.println("============= SELLER  2-2-2-2-2 ========  " + seller.getId());
        int tempCounter = 0;
        boolean needNewElement = true;
//        for (FullPrice fullPrice : fullPricesForXlsx) {
        for (TotalPrice sellerPrice : uniqueSellerPricesList) {
            FullPrice addToPrice = null;
//            for (TotalPrice sellerPrice : uniqueSellerPricesList) {
            for (FullPrice fullPrice : fullPricesForXlsx) {
//
//                System.out.println("fullPrice.getArticle().toUpperCase() = " + fullPrice.getArticle().toUpperCase());
//                System.out.println("sellerPrice.getArticle().toUpperCase() = " + sellerPrice.getArticle().toUpperCase());
//                System.out.println("fullPrice.getBrand().toUpperCase() = " + fullPrice.getBrand().toUpperCase());
//                System.out.println("sellerPrice.getIdBrand().getBrandName().toUpperCase() = " + sellerPrice.getIdBrand().getBrandName().toUpperCase());

//               элемент с брендом и артикулом найден, - добавляем цену и состояние склада
                if (fullPrice.getArticle().toUpperCase().equals(sellerPrice.getArticle().toUpperCase())
                        && fullPrice.getBrand().toUpperCase().equals(sellerPrice.getIdBrand().getBrandName().toUpperCase())) {
                    tempCounter++;
//                    System.out.println("tempCounter = " + tempCounter);

                    BigDecimal price = sellerPrice.getPrice();
                    price = calcRealPrice(seller, price);

                    fullPrice.setSellerPrice2(price);

                    fullPrice.setSellerStock2(sellerPrice.getOnStock());
                    System.out.println("добавляем цену в карточку товара = " + fullPrice);
                    needNewElement = false;
                    break;
                }
//               элемент с брендом и артикулом не найден, - заводим артикул, название добавляем цену и состояние склада

            }
            if (needNewElement == true) {

                addToPrice = new FullPrice();
                addToPrice.setBrand(sellerPrice.getIdBrand().getBrandName().toUpperCase());
                addToPrice.setArticle(sellerPrice.getArticle());
                addToPrice.setProductName(sellerPrice.getProductName());

                BigDecimal price = sellerPrice.getPrice();
                price = calcRealPrice(seller, price);
                addToPrice.setSellerPrice2(price);
                addToPrice.setSellerStock2(sellerPrice.getOnStock());

                fullPricesForXlsx.add(addToPrice);
//                System.out.println("Добавляем элемент в прайс = " + addToPrice);
            }
            needNewElement = true;

        }
//        System.out.println(" ===================== " );
//        for (FullPrice fullPrice : fullPricesForXlsx) {
//            System.out.println("FULL Price = " + fullPrice);
//        }
    }

    private void addSeller3(ArrayList<FullPrice> fullPricesForXlsx, Seller seller) {
        // Логика для одного продавца
//        for (FullPrice fullPrice : fullPricesForXlsx) {
//            System.out.println("FULL Price before = " + fullPrice);
//        }
        //        запрос данных из БД по собственному прайсу
        LocalDate dateNow = LocalDate.now();
        List<TotalPrice> sellerPriceFromDB;
        sellerPriceFromDB = totalPriceRepo.findByDateAndIdSaler(dateNow, seller);
        // Используем Set для удаления дубликатов
        Set<TotalPrice> uniqueSellerPricesSet = new HashSet<>(sellerPriceFromDB);
        // Преобразуем Set обратно в List
        List<TotalPrice> uniqueSellerPricesList = new ArrayList<>(uniqueSellerPricesSet);

        System.out.println("============= SELLER  3-3-3-3-3 ========  " + seller.getId());
        int tempCounter = 0;
        boolean needNewElement = true;
//        for (FullPrice fullPrice : fullPricesForXlsx) {
        for (TotalPrice sellerPrice : uniqueSellerPricesList) {
            FullPrice addToPrice = null;
//            for (TotalPrice sellerPrice : uniqueSellerPricesList) {
            for (FullPrice fullPrice : fullPricesForXlsx) {

//                System.out.println("fullPrice.getArticle().toUpperCase() = " + fullPrice.getArticle().toUpperCase());
//                System.out.println("sellerPrice.getArticle().toUpperCase() = " + sellerPrice.getArticle().toUpperCase());
//                System.out.println("fullPrice.getBrand().toUpperCase() = " + fullPrice.getBrand().toUpperCase());
//                System.out.println("sellerPrice.getIdBrand().getBrandName().toUpperCase() = " + sellerPrice.getIdBrand().getBrandName().toUpperCase());

//               элемент с брендом и артикулом найден, - добавляем цену и состояние склада
                if (fullPrice.getArticle().toUpperCase().equals(sellerPrice.getArticle().toUpperCase())
                        && fullPrice.getBrand().toUpperCase().equals(sellerPrice.getIdBrand().getBrandName().toUpperCase())) {
                    tempCounter++;
//                    System.out.println("tempCounter = " + tempCounter);

                    BigDecimal price = sellerPrice.getPrice();
                    price = calcRealPrice(seller, price);

                    fullPrice.setSellerPrice3(price);

                    fullPrice.setSellerStock3(sellerPrice.getOnStock());
                    System.out.println("добавляем цену в карточку товара = " + fullPrice);
                    needNewElement = false;
                    break;
                }
//               элемент с брендом и артикулом не найден, - заводим артикул, название добавляем цену и состояние склада
            }
            if (needNewElement == true) {

                addToPrice = new FullPrice();
                addToPrice.setBrand(sellerPrice.getIdBrand().getBrandName().toUpperCase());
                addToPrice.setArticle(sellerPrice.getArticle());
                addToPrice.setProductName(sellerPrice.getProductName());

                BigDecimal price = sellerPrice.getPrice();
                price = calcRealPrice(seller, price);
                addToPrice.setSellerPrice3(price);
                addToPrice.setSellerStock3(sellerPrice.getOnStock());

                fullPricesForXlsx.add(addToPrice);
//                System.out.println("Добавляем элемент в прайс  = " + addToPrice);
            }
            needNewElement = true;

        }
//        System.out.println(" ===================== " );
//        for (FullPrice fullPrice : fullPricesForXlsx) {
//            System.out.println("FULL Price after = " + fullPrice);
//        }
    }

    private BigDecimal calcRealPrice(Seller seller, BigDecimal price) {

        LocalDate localDate = LocalDate.now();
        BigDecimal sellerCourse = BigDecimal.valueOf(1);
        List<CurrencyRate> currencyRates = currencyRateRepo.findByDate(localDate);
//        System.out.println("currencyRates = " + currencyRates);
//        System.out.println("seller.getEconomicRules().getCurrencyCode() = " + seller.getEconomicRules().getCurrencyCode());
        if (!seller.getEconomicRules().getCurrencyCode().equals(933)) {
            for (CurrencyRate currencyRate : currencyRates) {
                if (currencyRate.getCurrency().equals(seller.getEconomicRules().getCurrencyCode())) {
                    sellerCourse = currencyRate.getCurrencyRate();
                    break;
                }
            }
        }

//        System.out.println("sellerCourse = " + sellerCourse);
//        System.out.println("price = " + price);
        price = price.multiply(sellerCourse);

//        System.out.println("price * currencyCourse = " + price);
//        System.out.println("currencyCoefficient = " + seller.getEconomicRules().getCurrencyCoefficient());
        price = price.multiply(seller.getEconomicRules().getCurrencyCoefficient());
//        System.out.println("price * currencyCoefficient = " + price);
        price = price.multiply(seller.getEconomicRules().getDeliveryCoefficient());
//        System.out.println("price * deliveryCoefficient = " + price);
        BigDecimal vatCoeff = BigDecimal.valueOf(100);
//        System.out.println("vatCoeff = 100? ??? " + vatCoeff);
        vatCoeff = vatCoeff.add(seller.getEconomicRules().getVatPercentInPrice());
//        System.out.println("vatCoeff = 100?+ процент из БД  ??? " + vatCoeff + " процент из БД = " + seller.getEconomicRules().getVatPercentInPrice());
        vatCoeff = vatCoeff.divide(BigDecimal.valueOf(120), 6, RoundingMode.HALF_UP);

        price = price.divide(vatCoeff, 2, RoundingMode.HALF_UP);
//        System.out.println("vatCoeff = " + vatCoeff);
//        System.out.println("price * vatCoeff = " + price);

        return price;
    }


}






