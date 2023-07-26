package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "language")
@Getter @Setter @ToString
public class Language {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(targetEntity = BookEntity.class, mappedBy = "language")
    private List<BookEntity> book;

}
