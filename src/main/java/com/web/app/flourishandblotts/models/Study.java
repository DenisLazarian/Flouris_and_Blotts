package com.web.app.flourishandblotts.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "studies")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(targetEntity = UserEntity.class, fetch = FetchType.LAZY, mappedBy = "study")
    private List<UserEntity> students;

}
