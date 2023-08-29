package com.web.app.flourishandblotts.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "editorial")
@Getter @Setter @ToString
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(targetEntity = BookEntity.class, mappedBy = "editorial")
    @JsonBackReference
    private Set<BookEntity> book;




}
