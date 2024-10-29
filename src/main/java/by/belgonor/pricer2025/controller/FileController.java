package by.belgonor.pricer2025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.File;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {

    @PostMapping("/api/files")
    public ResponseEntity<List<String>> getFiles(@RequestBody Map<String, String> request) {
        String folderPath = request.get("folderPath");
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<String> fileNames = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }
        return ResponseEntity.ok(fileNames);
    }
}
