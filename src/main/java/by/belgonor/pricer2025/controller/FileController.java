package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.dto.SellerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {
    public static class SellerDTO {
        private String priceName;
        private boolean isGeneralPrice;


        @PostMapping("/api/files")
        public ResponseEntity<List<String>> getFiles(@RequestBody Map<String, String> request) {
            String folderPath = request.get("folderPath");
            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();
            List<String> fileNames = new ArrayList<>();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
//                    fileNames.add(file.getName());
                        fileNames.add(file.getAbsolutePath());
                    }
                }
            }
            return ResponseEntity.ok(fileNames);
        }

//    @PostMapping("/api/files/compare")
//    public String createCompareFile() {
//// Предполагаем, что метод generateFile() возвращает полное название файла
////        String fileName = XlsxCompareFiles.generateFile();
//        String fileName = "файл называется XlsxCompareFiles.generateFile()";
//        return fileName;
//    }


        // Геттеры и сеттеры
        public String getPriceName() {
            return priceName;
        }

        public void setPriceName(String priceName) {
            this.priceName = priceName;
        }

        public boolean isGeneralPrice() {
            return isGeneralPrice;
        }

        public void setGeneralPrice(boolean isGeneralPrice) {
            this.isGeneralPrice = isGeneralPrice;
        }
    }

    @PostMapping("/api/files/compare")
    public String createCompareFile(@RequestBody List<SellerDTO> sellers) {
// Пример использования данных поставщиков
        for (SellerDTO seller : sellers) {
            System.out.println("Price Name: " + seller.getPriceName());
            System.out.println("Is General Price: " + seller.isGeneralPrice());
        }

// Генерация файла на основе данных поставщиков
//        String fileName = XlsxCompareFiles.generateFile(sellers);
        String fileName = "Тут будет имя файла";
        return fileName;
    }
}


