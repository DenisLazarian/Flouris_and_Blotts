package com.web.app.flourishandblotts.controllers;


import com.web.app.flourishandblotts.controllers.request.CreateUserDTO;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.RoleRepository;
import com.web.app.flourishandblotts.repositories.UserRepository;
import com.web.app.flourishandblotts.services.UserEntityService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserRepository userRepository;

    @Resource
    private UserEntityService userService;

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

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO){
        return ResponseEntity.ok(this.userService.createUser(createUserDTO));
    }


    @PutMapping("/{id}") // we use Put if we modify all properties of an object. In case we don't need to change all properties, we use a PATCH.
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserEntity userEntity){

        if(this.userRepository.getById(id).isEmpty()) return ResponseEntity.notFound().build();

        UserEntity userUpdated = this.userService.updateUser(userEntity, id);

        return ResponseEntity.ok().body(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);

        return ResponseEntity.ok().body("User deleted with exit.");
    }

    @GetMapping("/list")
    public ResponseEntity<?> listUser() {
        List<UserEntity> userList= this.userService.list();
        return ResponseEntity.ok(userList);
    }


}
