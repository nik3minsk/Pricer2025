package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.entity.AlfaBankCourses;
import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.repository.SellerRepo;
import by.belgonor.pricer2025.service.AlfaBankCoursesService;
import by.belgonor.pricer2025.service.RulesForXlsxService;
import by.belgonor.pricer2025.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
public class FileController {
    @Autowired
    private AlfaBankCoursesService alfaBankCoursesService;

    @Autowired
    private RulesForXlsxService rulesForXlsxService;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private SellerService sellerService;

    public static class SellerDTO {
        private AlfaBankCourses bank;
        private String priceName;
        private boolean isGeneralPrice;


        @GetMapping("/api/main")
        public String mainListener() {
            return "Hello World!";
        }

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

        public AlfaBankCourses getBank() {
            return bank;
        }

        public void setBank(AlfaBankCourses bank) {
            this.bank = bank;
        }
    }


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
                    System.out.println("file = " + file);
                }
            }
        }
        return ResponseEntity.ok(fileNames);
    }


    @PostMapping("/api/files/compare")
//    public String createCompareFile(@RequestBody List<SellerDTO> sellers) throws IOException {
    public String createCompareFile(@RequestBody List<Seller> sellers) throws IOException {
// Пример использования данных поставщиков
//        System.out.println("sellers = " + sellers);
        List<Seller> fullSellersList = new ArrayList<>();

        System.out.println(" ===== Получение недостающих данных для каждого Seller из репозитория  " );
        for (Seller seller : sellers) {
            // Получение недостающих данных для каждого Seller из репозитория
            Optional<Seller> sellerFromRepo = sellerService.findById(seller.getId());
            if (sellerFromRepo.isPresent()) {
                fullSellersList.add(sellerFromRepo.get());
            } else {
                System.out.println("Seller not found for ID: " + seller.getId());
            }
        }
//        AlfaBankCoursesService.start();
            alfaBankCoursesService.bigStart(fullSellersList);
//        AlfaBankCoursesService.getAlfaNB();



        String fileName = "Тут будет имя файла";
        return fileName;
    }
}
