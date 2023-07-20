package com.web.app.flourishandblotts.controllers.request;

import com.web.app.flourishandblotts.models.Penalization;
import com.web.app.flourishandblotts.models.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    private String dni_nie;

    private String password;

    private String name;


    private String surname1;

    private String surname2;


    private String mail;

    private String status; // Active o Inactive



    private Date lastModifiedDate;

    private String course;

    private String group;

    private String studyId;



    private Penalization penalization;


    private Set<String> roles;
}
