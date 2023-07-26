package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.id.uuid.StandardRandomStrategy;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
@Getter @Setter @ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private boolean status;


    private Date disponibility_date;


    @ManyToOne(targetEntity = BookEntity.class)
    @JoinColumn(name = "book_id")
    private BookEntity book;


    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

}
