package com.web.app.flourishandblotts.controllers;


import com.web.app.flourishandblotts.controllers.request.CreateUserDTO;
import com.web.app.flourishandblotts.models.ERole;
import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.RoleRepository;
import com.web.app.flourishandblotts.repositories.UserRepository;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @GetMapping("/hello")
    public String hello(){
        return "Hello world not secured";
    }

    @GetMapping("/hello2")
    public String helloSecured(){
        return "Hello secured";
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO){
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(roleName -> {
                    ERole role = ERole.valueOf(roleName);
                    return roleRepository.findByName(role).orElseGet(() -> {
                        RoleEntity newRole = RoleEntity.builder().name(role).build();
                        return roleRepository.save(newRole);
                    });
                })
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .dniNie(createUserDTO.getDni_nie())
                .password(this.passwordEncoder.encode(createUserDTO.getPassword()))
                .name(createUserDTO.getName())
                .surname1(createUserDTO.getSurname1())
                .surname2(createUserDTO.getSurname2())
                .mail(createUserDTO.getMail())
                .status(createUserDTO.getStatus())
                .lastModifiedDate(new Date(new java.util.Date().getTime()))
                .roles(roles)
                .penalization(null)
                .build();

        this.userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.userRepository.deleteById(id);
        return ResponseEntity.ok().body("User deleted with exit.");
    }


}
