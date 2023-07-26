package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book_copy")
@Getter @Setter @ToString
public class BookCopy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private boolean canUser;

    private String qrCode;

    @ManyToOne
    private BookEntity book;
}
