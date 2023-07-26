package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "penalization")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter @ToString
public class Penalization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date finishPenalizationDate;

    @OneToOne
    private UserEntity user;

}
