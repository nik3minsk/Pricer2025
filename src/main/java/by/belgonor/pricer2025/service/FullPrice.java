package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.Seller;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Getter
@Setter
public class FullPrice {
    String barcode;
    String tnvedCode;
    String brand;
    String article;
    String productName;

    String onStock;
    String onStockReserved;
    String onStockFree;

    BigDecimal retailPrice;
    BigDecimal ourPrice;

    BigDecimal sellerPrice1;
    String sellerStock1;
    BigDecimal sellerPrice2;
    String sellerStock2;
    BigDecimal sellerPrice3;
    String sellerStock3;

    @Override
    public String toString() {
        return "FullPrice{" +
                "barcode='" + barcode + '\'' +
                ", tnvedCode='" + tnvedCode + '\'' +
                ", brand='" + brand + '\'' +
                ", article='" + article + '\'' +
                ", productName='" + productName + '\'' +
                ", onStock='" + onStock + '\'' +
                ", onStockReserved=" + onStockReserved +
                ", inStockFree=" + onStockFree +
                ", retailPrice=" + retailPrice +
                ", ourPrice=" + ourPrice +
                ", sellerPrice1=" + sellerPrice1 +
                ", sellerStock1=" + sellerStock1 +
                ", sellerPrice2=" + sellerPrice2 +
                ", sellerStock2=" + sellerStock2 +
                ", sellerPrice3=" + sellerPrice3 +
                ", sellerStock3=" + sellerStock3 +
                '}';
    }

    public static Path saveXLSX(ArrayList<FullPrice> fullPricesForXlsx, List<String> sellersNames) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BG_price_comparer");

        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 14000);
        sheet.setColumnWidth(5, 2500);
        sheet.setColumnWidth(6, 2500);
        sheet.setColumnWidth(7, 2500);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 3000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 3000);
        sheet.setColumnWidth(12, 3000);
        sheet.setColumnWidth(13, 3000);
        sheet.setColumnWidth(14, 3000);
        sheet.setColumnWidth(15, 3000);

        Row headerRow = sheet.createRow(0);

        //        прописываем стиль заголовка 12 высота, шрифт Ариал, фон синий
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFColor lightBlue = new XSSFColor(new java.awt.Color(224, 255, 255), new DefaultIndexedColorMap());
        headerStyle.setFillForegroundColor(lightBlue);
//        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);


        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Barcode");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("TNVED CODE");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("BRAND");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("ARTICLE");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("PRODUCT");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("ONSTOCK");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("RESERV");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(7);
        headerCell.setCellValue("FREE");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(8);
        headerCell.setCellValue("Out_PRICE");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(9);
        headerCell.setCellValue("IN_PRICE");
        headerCell.setCellStyle(headerStyle);

//        заголовок для 1-го поставщика будет в любом случае

        headerCell = headerRow.createCell(10);
        headerCell.setCellValue(sellersNames.get(0));
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(11);
        headerCell.setCellValue(sellersNames.get(0) + " склад");
        headerCell.setCellStyle(headerStyle);

        //        заголовок для 2-го поставщика
        if (sellersNames.size() >= 2) {
            headerCell = headerRow.createCell(12);
            headerCell.setCellValue(sellersNames.get(1));
            headerCell.setCellStyle(headerStyle);

            headerCell = headerRow.createCell(13);
            headerCell.setCellValue(sellersNames.get(1) + " склад");
            headerCell.setCellStyle(headerStyle);

            //        заголовок для 2-го поставщика

            if (sellersNames.size() >= 3) {
                headerCell = headerRow.createCell(14);
                headerCell.setCellValue(sellersNames.get(2));
                headerCell.setCellStyle(headerStyle);

                headerCell = headerRow.createCell(15);
                headerCell.setCellValue(sellersNames.get(2) + " склад");
                headerCell.setCellStyle(headerStyle);
            }
        }

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);


        //     ?   прописываем стиль заголовка 12 высота, шрифт Ариал, фон белый
        CellStyle standartStyle = workbook.createCellStyle();
        standartStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
//        standartStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        standartStyle.setAlignment(HorizontalAlignment.RIGHT);
// ?
        DataFormat format = workbook.createDataFormat();
        standartStyle.setDataFormat(format.getFormat("0.00"));


        XSSFFont standartFont = ((XSSFWorkbook) workbook).createFont();
        standartFont.setFontName("Arial");
        standartFont.setFontHeightInPoints((short) 11);
//        standartFont.setBold(true);
        standartStyle.setFont(standartFont);


        //     ?   прописываем стиль заголовка 12 высота, шрифт Ариал, фон белый
        CellStyle standartStyleLeft = workbook.createCellStyle();
        standartStyleLeft.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
//        standartStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        standartStyleLeft.setAlignment(HorizontalAlignment.LEFT);
// ?
        DataFormat formatLeft = workbook.createDataFormat();
        standartStyleLeft.setDataFormat(formatLeft.getFormat("0.00"));


        XSSFFont standartFontLeft = ((XSSFWorkbook) workbook).createFont();
        standartFontLeft.setFontName("Arial");
        standartFontLeft.setFontHeightInPoints((short) 11);
//        standartFont.setBold(true);
        standartStyleLeft.setFont(standartFontLeft);



        //    ?    зеленый фон ячеек для выделения минимальных цен
        CellStyle greenStyle = workbook.createCellStyle();
        greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        XSSFFont standartFont = ((XSSFWorkbook) workbook).createFont();
        standartFont.setFontName("Arial");
        standartFont.setFontHeightInPoints((short) 11);
        greenStyle.setAlignment(HorizontalAlignment.RIGHT);
// ?
        DataFormat formatGreen = workbook.createDataFormat();
        greenStyle.setDataFormat(formatGreen.getFormat("0.00"));
        greenStyle.setFont(standartFont);


        Cell cell = null;
        for (int i = 0; i < fullPricesForXlsx.size(); i++) {
            final Row row = sheet.createRow(1 + i);

            cell = row.createCell(0);
            cell.setCellValue(fullPricesForXlsx.get(i).barcode);
            cell.setCellStyle(standartStyle);
            cell = row.createCell(1);
            cell.setCellValue(fullPricesForXlsx.get(i).tnvedCode);
            cell.setCellStyle(standartStyle);
            cell = row.createCell(2);
            cell.setCellValue(fullPricesForXlsx.get(i).brand);
            cell.setCellStyle(standartStyle);
            cell = row.createCell(3);
            cell.setCellValue(fullPricesForXlsx.get(i).article);
            cell.setCellStyle(standartStyle);
            cell = row.createCell(4);
            cell.setCellValue(fullPricesForXlsx.get(i).productName);
            cell.setCellStyle(standartStyleLeft);
            cell = row.createCell(5);
            cell.setCellValue(fullPricesForXlsx.get(i).onStock);
            cell.setCellStyle(standartStyle);
            cell = row.createCell(6);
            cell.setCellValue(fullPricesForXlsx.get(i).onStockReserved);
            cell.setCellStyle(standartStyle);
            cell = row.createCell(7);
            cell.setCellValue(fullPricesForXlsx.get(i).onStockFree);
            cell.setCellStyle(standartStyle);

            cell = row.createCell(8);
//            BigDecimal roundedValue = fullPricesForXlsx.get(i).retailPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
//            cell.setCellValue(roundedValue.floatValue());
            cell.setCellValue(fullPricesForXlsx.get(i).retailPrice.floatValue());
            cell.setCellStyle(standartStyle);

            cell = row.createCell(9);
            cell.setCellValue(fullPricesForXlsx.get(i).ourPrice.floatValue());
            cell.setCellStyle(standartStyle);

//            добавление поставщиков и подкрашивание минимальной цены
            BigDecimal minPrice = minPrice(fullPricesForXlsx.get(i).sellerPrice1, fullPricesForXlsx.get(i).sellerPrice2, fullPricesForXlsx.get(i).sellerPrice3);

            cell = row.createCell(10);
            cell.setCellValue(fullPricesForXlsx.get(i).sellerPrice1.floatValue());
            if (fullPricesForXlsx.get(i).getSellerPrice1().equals(minPrice) && !fullPricesForXlsx.get(i).sellerPrice2.equals(BigDecimal.ZERO)) {
                cell.setCellStyle(greenStyle);
            }
            else {
                cell.setCellStyle(standartStyle);
            }

            cell = row.createCell(11);
            cell.setCellValue(fullPricesForXlsx.get(i).sellerStock1);
            cell.setCellStyle(standartStyle);

            if (sellersNames.size() >= 2) {

                cell = row.createCell(12);
                cell.setCellValue(fullPricesForXlsx.get(i).sellerPrice2.floatValue());
                if (fullPricesForXlsx.get(i).getSellerPrice2().equals(minPrice) && !fullPricesForXlsx.get(i).sellerPrice2.equals(BigDecimal.ZERO)) {
                    cell.setCellStyle(greenStyle);
                }
                else {
                    cell.setCellStyle(standartStyle);
                }

                cell = row.createCell(13);
                cell.setCellValue(fullPricesForXlsx.get(i).sellerStock2);
                cell.setCellStyle(standartStyle);

                if (sellersNames.size() >= 3) {

                    cell = row.createCell(14);
                    cell.setCellValue(fullPricesForXlsx.get(i).sellerPrice3.floatValue());
                    if (fullPricesForXlsx.get(i).getSellerPrice3().equals(minPrice) && !fullPricesForXlsx.get(i).sellerPrice2.equals(BigDecimal.ZERO)) {
                        cell.setCellStyle(greenStyle);
                    }
                    else {
                        cell.setCellStyle(standartStyle);
                    }

                    cell = row.createCell(15);
                    cell.setCellValue(fullPricesForXlsx.get(i).sellerStock3);
                    cell.setCellStyle(standartStyle);

                }

            }
        }

        File currDir = new File(".");
//        String path = currDir.getAbsolutePath();
        String path = "/Users/nik3minsk/IdeaProjects/pricer2025/xlsx/result/";

        java.time.LocalDateTime currentDateTime = java.time.LocalDateTime.now();

//        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";
//        String fileLocation = path.substring(0, path.length() - 1) + "compare_" + currentDateTime.toString().substring(0, 19) + ".xlsx";

        String fileLocation = path + "compare_result_from_" + currentDateTime.toString().replace(":", "_") + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
        return Path.of(fileLocation);
    }

    private static BigDecimal minPrice(BigDecimal a, BigDecimal b, BigDecimal c) {
        BigDecimal minValue = null;
        if (a != null && a.compareTo(BigDecimal.ZERO) > 0) {
            minValue = a;
        }
        if (b != null && b.compareTo(BigDecimal.ZERO) > 0 && (minValue == null || b.compareTo(minValue) < 0)) {
            minValue = b;
        }
        if (c != null && c.compareTo(BigDecimal.ZERO) > 0 && (minValue == null || c.compareTo(minValue) < 0)) {
            minValue = c;
        }
        return minValue != null ? minValue : BigDecimal.ZERO;
    }

}
