package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;

import com.web.app.flourishandblotts.models.UserEntity;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment")
@Getter @Setter @ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    private UserEntity user;



    @ManyToOne(targetEntity = BookEntity.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;



    private String text;

}
