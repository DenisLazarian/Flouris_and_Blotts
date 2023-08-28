package com.web.app.flourishandblotts.models;

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

    private int pageNumber;


    @Column(columnDefinition = "TEXT")
    private String description;


    private String thumbnail;

    @ManyToOne(targetEntity = Language.class)
    private Language language;

    @ManyToOne(targetEntity = Editorial.class)
    private Editorial editorial;

    @ManyToMany(
            targetEntity = Category.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;



    @ManyToMany(
            targetEntity = Author.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Author> authors;



}
