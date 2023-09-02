package com.web.app.flourishandblotts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.web.app.flourishandblotts.controllers.response.BookSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
@Getter @Setter @ToString
@JsonSerialize(using = BookSerializer.class)
public class BookEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ISBN_13")
    private String isbn_13;


//    @Column(unique = true)
    @NotBlank
    private String title;

//    @Column(unique = true)
    @NotBlank
    private String subtitle;

    private String datePublished;

    private Integer pageNumber;


    @Column(columnDefinition = "TEXT")
    private String description;


    private String thumbnail;

//    @JsonIgnore(value = false)
    @ManyToOne(
            targetEntity = Language.class,
            fetch = FetchType.EAGER
    )
//    @JsonManagedReference
    private Language language;

//    @JsonIgnore(value = false)
    @ManyToOne(
            targetEntity = Editorial.class,
            fetch = FetchType.EAGER
    )
//    @JsonManagedReference
    private Editorial editorial;

//    @JsonIgnore(value = false)
    @ManyToMany(
            targetEntity = Category.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;



//    @JsonIgnore(value = false)
    @ManyToMany(
            targetEntity = Author.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Author> authors;



}
