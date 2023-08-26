package com.web.app.flourishandblotts.controllers.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseObjectBook {
    private int totalItems;

    private String id;

    private String title;

    private String publisher;

    private int pageCount;

    private String language;
}
