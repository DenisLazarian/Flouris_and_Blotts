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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasRole('ADMIN_PRICIPAL')")
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
    @PreAuthorize("hasRole('ADMIN_PRICIPAL')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserEntity userEntity){

        if(this.userRepository.getById(id).isEmpty()) return ResponseEntity.notFound().build();

        UserEntity userUpdated = this.userService.updateUser(userEntity, id);

        return ResponseEntity.ok().body(userUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_PRICIPAL')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok().body("User deleted with exit.");
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN_PRICIPAL')")
    public ResponseEntity<?> listUser() {
        List<UserEntity> userList= this.userService.list();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("prove/file")
    public ResponseEntity<?> prove(@RequestBody String algo){
        System.out.println(algo);
        return ResponseEntity.ok(algo);
    }

//    @GetMapping("role")
//    public ResponseEntity<Map<String, Object>> checkRole(@RequestParam String[] roles, @RequestHeader("Authorization") String bearer){
//
//        System.out.println(roles.length);
//
//        Map<String, Object> response = new HashMap<>();
//
//
//        response.put("message", "Checking role");
//        response.put("response", this.userService.checkRole(roles,bearer));
//
//        return  ResponseEntity.ok(response);
//    }

    @GetMapping("role")
    public ResponseEntity<Map<String,Object>> getUserRole() {
        // Obtiene la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("--auth--");
        System.out.println(authentication);

        Map<String,Object> response = new HashMap<>();

        // Verifica si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            String rolObtained = authentication.getAuthorities().stream()
                    .map(Object::toString)
                    .findFirst()
                    .orElse("USER_READER");


            response.put("message", "User roles");
            response.put("response", rolObtained);

            return ResponseEntity.ok(response);
                     // En caso de que no se encuentren roles, se asume ROLE_USER
        }

        // Si no hay usuario autenticado, se devuelve un valor predeterminado
        response.put("message", "Not authenticated");
        response.put("response", "ANONYMOUS");
        return ResponseEntity.ok(response);
    }

}
