package com.web.app.flourishandblotts.controllers.request;

import com.web.app.flourishandblotts.models.Penalization;
import com.web.app.flourishandblotts.models.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private String study;

    private String name_course;



    private Penalization penalization;


    private Set<String> roles;
}
