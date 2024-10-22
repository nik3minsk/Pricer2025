package by.belgonor.pricer2025.controller;


import by.belgonor.pricer2025.entity.Cat;
import by.belgonor.pricer2025.repository.CatRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {


    @Autowired
    private CatRepo catRepo;
    private ObjectMapper objectMapper;

    @GetMapping("/api/main")
    public String mainListener(){
        return "Hello World!";
    }

    @GetMapping("/api/cat")
    public String giveCatListener(){
        Cat cat = new Cat("Barsik", 5, 10);
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            System.out.println("Error with cat class e = " + e);
        }
        return jsonData;
    }

    @PostMapping("/api/special")
    public String giveCatListenerPost(@RequestParam String name){
        Cat cat = new Cat(name, 5, 10);
//        Cat cat1 = new Cat(name, 5, 10);

        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            System.out.println("Error with cat class e = " + e);
        }
        return jsonData;
    }

    @PostMapping("/api/currency")
    public String giveCurrencyValuePost(@RequestParam String value, String value1){
        Cat cat = new Cat(value, 5, Integer.valueOf(value1));
//        Cat cat1 = new Cat(name, 5, 10);
        System.out.println("value = " + value);
        System.out.println("value1 = " + value1);

        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            System.out.println("Error with cat class e = " + e);
        }
        return jsonData;
    }

//
//    @PostMapping("/api/log")
//    public String log(){
////        Cat cat1 = new Cat(name, 5, 10);
//
//        String jsonData = null;
//        try {
//            jsonData = objectMapper.writeValueAsString(cat);
//        } catch (JsonProcessingException e) {
////            throw new RuntimeException(e);
//            System.out.println("Error with cat class e = " + e);
//        }
//        return jsonData;
//    }

@PostMapping("/api/nbcurrency")
public String getNbCurrency(@RequestBody Map<String, String> payload){
    String date = payload.get("date");
    System.out.println("Received date: " + date);
    return "Received date: " + date;
}


    @PostMapping("/api/add")
    public void addCat(@RequestBody Cat cat){
        log.info("New Row: " + catRepo.save(cat));
//        catRepo.save(cat);
    }

    @GetMapping("/api/all")
    public List<Cat> getAllCats(){
        return catRepo.findAll();
    }

    @GetMapping("/api/cat-manage")
    public Cat getCatById(@RequestParam int id){
//        return catRepo.findById(id).get();
        return catRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/api/cat-manage")
    public void deleteCatById(@RequestParam int id){
        catRepo.deleteById(id);
    }

    @PutMapping("/api/cat-manage")
    public void updateCat(@RequestBody Cat cat){
        if(!catRepo.existsById(cat.getId())){
            catRepo.save(cat);
        }
        log.info("Updated row: " + catRepo.save(cat));
        return ;
    }

}
