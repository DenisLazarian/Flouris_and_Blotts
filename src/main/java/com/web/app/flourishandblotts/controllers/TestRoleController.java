package com.web.app.flourishandblotts.controllers;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRoleController {


    @GetMapping("/accessAdminP")
    @PreAuthorize("hasRole('ADMIN_PRICIPAL')")
    public String adminAccess(){
        return "Acceso con rol de admin principal";
    }
    @GetMapping("/accessAdminR")
    @PreAuthorize("hasRole('ADMIN_RESPONSIBLE')")
    public String responsibleAccess(){
        return "Acceso, con rol de admin responsable";
    }

    @GetMapping("/accessUser")
    @PreAuthorize("hasRole('USER_READER')")
    public String readerAccess(){
        return "Acceso con rol de alumno lector";
    }


}
