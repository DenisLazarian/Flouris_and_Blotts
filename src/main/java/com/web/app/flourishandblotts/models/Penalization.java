package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "penalization")
public class Penalization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date finishPenalizationDate;

    @OneToOne
    private UserEntity student;

}
