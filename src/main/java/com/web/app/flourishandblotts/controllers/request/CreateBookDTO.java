package com.web.app.flourishandblotts.controllers.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookDTO {

    private String isbn_13;

    private String title;

    private String subtitle;

    private Date datePublished;

    private int pageNumber;

    private String description;

    private String imageFile;

    private String language;

    private String editorial;

    private Set<String> categories;

    private Set<String> authors;

}
