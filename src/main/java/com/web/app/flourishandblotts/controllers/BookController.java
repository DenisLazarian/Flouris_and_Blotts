package com.web.app.flourishandblotts.controllers;

import com.web.app.flourishandblotts.controllers.request.CreateBookDTO;
import com.web.app.flourishandblotts.models.BookEntity;
import com.web.app.flourishandblotts.services.BookService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("book")
public class BookController {
    @Resource
    private BookService bookService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> bookList(){

        Map<String, Object> response = new HashMap<>();
////        if(this.bookService.listBooks().size() > 0){
            response.put("status", "200");
            response.put("message", "List of books");
            response.put("response", this.bookService.listBooks());
//
//            return ResponseEntity.ok(response);

//        }else return ResponseEntity.noContent().build();
        System.out.println(this.bookService.listBooks().size());
        return ResponseEntity.ok(response);
    }

    @PostMapping("create")
    public BookEntity createBook(CreateBookDTO createBookDTO){
        return this.bookService.createBook(createBookDTO);
    }
}
