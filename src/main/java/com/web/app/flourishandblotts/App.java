package com.web.app.flourishandblotts;

import com.web.app.flourishandblotts.models.ERole;
import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class App {

    @Resource
    UserRepository userRepository;

    @Resource
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner init(){
        return args -> {
            creatingUsers();
        };
    }







    public void creatingUsers(){
        UserEntity userEntity1 = UserEntity.builder()
                .mail("user@me.local")
                .name("user")
                .dniNie("0000003")
                .surname1("student")
                .surname2(null)
                .password(passwordEncoder.encode("1234"))
                .status("Active")
                .lastModifiedDate(new java.sql.Date(new java.util.Date().getTime()))
                .course("Bachiller")
                .penalization(null)
                .study(null)
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.USER_READER.name()))
                        .build()))
                .build();

        this.userRepository.save(userEntity1);

        UserEntity responsible = UserEntity.builder()
                .mail("responsible@me.local")
                .name("admin_resp")
                .dniNie("0000005")
                .surname1("Librarian 1")
                .surname2(null)
                .password(passwordEncoder.encode("1234"))
                .status("Active")
                .lastModifiedDate(new java.sql.Date(new java.util.Date().getTime()))
                .course("University")
                .penalization(null)
                .study(null)
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.ADMIN_RESPONSIBLE.name()))
                        .build()))
                .build();

        this.userRepository.save(responsible);

        UserEntity admin = UserEntity.builder()
                .mail("admin@me.local")
                .name("admin_principal")
                .dniNie("0000008")
                .surname1("administrator")
                .surname2(null)
                .password(passwordEncoder.encode("1234"))
                .status("Active")
                .lastModifiedDate(new java.sql.Date(new java.util.Date().getTime()))
                .course("University")
                .penalization(null)
                .study(null)
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.ADMIN_PRINCIPAL.name()))
                        .build()))
                .build();

        this.userRepository.save(admin);
    }




}
