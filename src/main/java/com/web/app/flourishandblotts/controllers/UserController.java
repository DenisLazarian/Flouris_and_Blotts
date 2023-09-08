package com.web.app.flourishandblotts.controllers;


import com.web.app.flourishandblotts.controllers.request.CreateUserDTO;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.UserRepository;
import com.web.app.flourishandblotts.services.UserEntityService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Resource
    private UserRepository userRepository;

    @Resource
    private UserEntityService userService;



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

    @PostMapping("/sessionData")
    public ResponseEntity<Map<String, Object>> getDataSession(@RequestBody String mail){
        JSONObject mailJSON = new JSONObject(mail);
        String mailString = mailJSON.get("mail").toString();

        UserEntity user = this.userService.findByMail(mailString);
        System.out.println("Getting data session "+ mailString);
        Map<String, Object> response = new HashMap<>();
        if(user != null){
            response.put("status", "200");
            response.put("Message", "User Session");
            response.put("response", user);

            return ResponseEntity.ok(response);
        }else{
            Map<String, Object> responseError = new HashMap<>();
            responseError.put("message", "Not authenticated");

            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(responseError);
        }
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

    @PostMapping("prove/file")
    public ResponseEntity<?> prove(@RequestBody String algo){
        System.out.println(algo);
        return ResponseEntity.ok(algo);
    }


}
