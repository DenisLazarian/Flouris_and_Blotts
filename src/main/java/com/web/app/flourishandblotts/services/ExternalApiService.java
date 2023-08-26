package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.controllers.request.ApiResponseObjectBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApiResponseObjectBook getApiResponse() {
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=subject:Mystery+adventure&maxResults=40"; // URL de la API externa
        ApiResponseObjectBook response = restTemplate.getForObject(apiUrl, ApiResponseObjectBook.class);


        return response;
    }
}
