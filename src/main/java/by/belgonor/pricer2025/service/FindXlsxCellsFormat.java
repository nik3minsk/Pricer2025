package by.belgonor.pricer2025.service;

import com.mysql.cj.util.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FindXlsxCellsFormat {


    //      не проверено
//    public static BigDecimal cellOfBigDecimal(XSSFRow row, Integer cellNumber) {
//        BigDecimal cellValue = BigDecimal.valueOf(0);
//        if (row.getCell(cellNumber) != null) {
//            switch (row.getCell(cellNumber).getCellType()) {
//                case STRING:
//                    cellValue = BigDecimal.valueOf(0);
//                    break;
//                case NUMERIC:
//                    cellValue = BigDecimal.valueOf(row.getCell(cellNumber).getNumericCellValue());
//                    break;
//                case FORMULA:
////                        result +=  cell.getNumericCellValue() ;
//                    break;
//                default:
////                        result += "|";
//                    break;
//            }
//        }
//        return cellValue;
//    }

    public static BigDecimal cellOfBigDecimal(XSSFRow row, Integer cellNumber) {
        BigDecimal cellValue = BigDecimal.valueOf(0);
        if (row.getCell(cellNumber) != null) {
            switch (row.getCell(cellNumber).getCellType()) {
                case STRING:
                    String stringValue = row.getCell(cellNumber).getStringCellValue();
                    // Удаляем пробелы спереди и сзади и заменяем запятые на точки
                    stringValue = stringValue.trim().replace(",", ".");
                    try {
                        cellValue = new BigDecimal(stringValue);
                    } catch (NumberFormatException e) {
                        // Логируем или обрабатываем ошибку, если строка не может быть конвертирована в BigDecimal
                        cellValue = BigDecimal.valueOf(0);
                    }
                    break;
                case NUMERIC:
                    cellValue = BigDecimal.valueOf(row.getCell(cellNumber).getNumericCellValue());
                    break;
                case FORMULA:
                    // Обработка формул (если необходимо)
                    break;
                default:
                    // Обработка остальных типов (если необходимо)
                    break;
            }
        }
        return cellValue;
    }



    public static Integer cellOfInteger(XSSFRow row, Integer cellNumber) {

        Integer cellValue = Integer.valueOf(0);
        if (row.getCell(cellNumber) != null) {
            switch (row.getCell(cellNumber).getCellType()) {
                case STRING:
                    if (StringUtils.isStrictlyNumeric(row.getCell(cellNumber).getStringCellValue())) {
                        String myString = row.getCell(cellNumber).getStringCellValue();
//                        System.out.println("myString = " + myString);
                        Integer myInt = Integer.valueOf(myString);
//                        System.out.println("myInt = " + myInt);
//                    cellValue = Integer.parseInt(row.getCell(cellNumber).getStringCellValue());
                        return myInt;
//                    cellValue = BigDecimal.valueOf(0);
                    }
                    break;
                case NUMERIC:
                    cellValue = Integer.valueOf((int) row.getCell(cellNumber).getNumericCellValue());
                    break;
                case FORMULA:
//                        result +=  cell.getNumericCellValue() ;
                    break;
                default:
//                        result += "|";
                    break;
            }
        }
        return cellValue;
    }

    public static String cellOfString(XSSFRow row, Integer cellNumber) {
//        String cellValue = null;
        String cellValue = "";
        if (row.getCell(cellNumber) != null) {
            switch (row.getCell(cellNumber).getCellType()) {
                case STRING:
                    cellValue = row.getCell(cellNumber).getStringCellValue();
                    break;
                case NUMERIC:
                    cellValue = String.valueOf(BigInteger.valueOf((long) row.getCell(cellNumber).getNumericCellValue()));
//                    cellValue = String.valueOf(cellValue);
                    break;
                case FORMULA:
//                        result +=  cell.getNumericCellValue() ;
                    break;
                default:
//                        result += "|";
                    break;
            }
        }
        return cellValue;
    }

    public static Long cellOfLong(XSSFRow row, Integer cellNumber) {
        Long cellValue = Long.valueOf(0);
        if (row.getCell(cellNumber) != null) {
            switch (row.getCell(cellNumber).getCellType()) {
                case STRING:
                    try {
                        cellValue = Long.valueOf(row.getCell(cellNumber).getStringCellValue());
                    }
                    catch (NumberFormatException e) {
                        cellValue = Long.valueOf(0);
                    }

                    break;
                case NUMERIC:
                    cellValue = Long.valueOf((long) row.getCell(cellNumber).getNumericCellValue());
                    break;
                case FORMULA:
//                        result +=  cell.getNumericCellValue() ;
                    break;
                default:
                    cellValue = 0L;
//                        result += "|";
                    break;
            }
        }
        return cellValue;
    }


    public static BigInteger cellOfBigInteger(XSSFRow row, Integer cellNumber) {
        BigInteger cellValue = BigInteger.valueOf(0);
        if (row.getCell(cellNumber) != null) {
            switch (row.getCell(cellNumber).getCellType()) {
                case STRING:
//                    if (String.valueOf(row.getCell(cellNumber)) == "") cellValue = BigInteger.valueOf(0);
                    String aaa = String.valueOf(row.getCell(cellNumber));
//                    cellValue = new BigInteger(String.valueOf(row.getCell(cellNumber)));

                    try {
                        cellValue = new BigInteger(String.valueOf(row.getCell(cellNumber)));
                    } catch (NumberFormatException e) {
                        System.out.println("ячейка была пуста и преобразована в значение 0");
                        cellValue = BigInteger.valueOf(0);
                    }
                    break;

                case NUMERIC:
                    cellValue = BigInteger.valueOf((long) row.getCell(cellNumber).getNumericCellValue());
                    break;
                case FORMULA:
//                        result +=  cell.getNumericCellValue() ;
                    break;
                default:
                    cellValue = BigInteger.valueOf(0);
//                        result += "|";
                    break;
            }
        }
        return cellValue;
    }

    public static Double cellOfDouble(XSSFRow row, Integer cellNumber) {
        Double cellValue = Double.valueOf(0);
        if (row.getCell(cellNumber) != null) {
            switch (row.getCell(cellNumber).getCellType()) {
                case STRING:
                    cellValue = Double.valueOf(0);
//                    cellValue = Long.valueOf(0);
                    break;
                case NUMERIC:
                    cellValue = Double.valueOf((row.getCell(cellNumber).getNumericCellValue()));
                    break;
                case FORMULA:
//                        result +=  cell.getNumericCellValue() ;
                    break;
                default:
                    cellValue = (double) 0;
//                        result += "|";
                    break;
            }
        }
        return cellValue;
    }


}
