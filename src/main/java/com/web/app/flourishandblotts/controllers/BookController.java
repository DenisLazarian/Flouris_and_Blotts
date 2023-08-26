package com.web.app.flourishandblotts.controllers;

import com.web.app.flourishandblotts.controllers.request.ApiResponseObjectBook;
import com.web.app.flourishandblotts.controllers.request.CreateBookDTO;
import com.web.app.flourishandblotts.services.BookService;
import com.web.app.flourishandblotts.services.ExternalApiService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final ExternalApiService externalApiService;

    @Autowired
    public BookController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/api/data")
    public ApiResponseObjectBook getDataFromExternalApi() {
        return externalApiService.getApiResponse();
    }

    @Resource
    BookService bookService;

    @GetMapping("exampleBooks")
    public CreateBookDTO getExampleBooks(){
        return bookService.searchBooks();
    }

}
