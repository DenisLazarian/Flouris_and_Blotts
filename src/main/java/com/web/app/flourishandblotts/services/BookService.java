package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.controllers.request.CreateBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class BookService {


    private final RestTemplate restTemplate;

    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CreateBookDTO searchBooks() {
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=subject:Mystery+adventure&maxResults=40";
        return restTemplate.getForObject(apiUrl, CreateBookDTO.class);
    }

    /**
     * Here we insert Books registers from Google Calendar.
     *
     * @serialData
     * */
    public void insertExampleBooks(){


    }

}
