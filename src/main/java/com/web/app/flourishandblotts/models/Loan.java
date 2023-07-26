package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "loan")
@Getter @Setter @ToString
public class Loan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private Date finishDate;

    private Date returnedDate;


    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    @ManyToOne(targetEntity = BookEntity.class)
    private BookEntity book;

    @ManyToOne(targetEntity = BookCopy.class)
    private BookCopy bookCopy;
}
