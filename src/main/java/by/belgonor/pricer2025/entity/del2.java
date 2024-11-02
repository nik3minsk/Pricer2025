//package by.belgonor.pricer2025.entity;
//
//import by.belgonor.pricer2025.controller.FileController;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class del2 {
//
//
//
//
//
//
//
//
//
//
//
//
//
//    package by.belgonor.pricer2025.controller;
//
//import by.belgonor.pricer2025.entity.AlfaBankCourses;
//import by.belgonor.pricer2025.service.AlfaBankCoursesService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.io.File;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//    @Slf4j
//    @RestController
//    public class FileController {
//        public static class SellerDTO {
//
//
//
//        @PostMapping("/api/files/compare")
//        public String createCompareFile(@RequestBody List<by.belgonor.pricer2025.controller.FileController.SellerDTO> sellers) throws IOException {
//// Пример использования данных поставщиков
//            for (by.belgonor.pricer2025.controller.FileController.SellerDTO seller : sellers) {
//                System.out.println("Price Name: " + seller.getPriceName());
//                System.out.println("Is General Price: " + seller.isGeneralPrice());
//            }
//
//
//            String fileName = "Тут будет имя файла";
//            return fileName;
//        }
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
