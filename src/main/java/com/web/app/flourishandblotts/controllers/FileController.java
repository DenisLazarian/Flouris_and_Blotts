package com.web.app.flourishandblotts.controllers;

import com.web.app.flourishandblotts.controllers.request.CreateUserDTO;
import com.web.app.flourishandblotts.models.ERole;
import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.Study;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.services.RoleService;
import com.web.app.flourishandblotts.services.StorageService;
import com.web.app.flourishandblotts.services.StudyService;
import com.web.app.flourishandblotts.services.UserEntityService;
import net.minidev.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.management.relation.Role;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Array;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("file")
public class FileController {
    public static final String SEPARATOR=";";
    public static final String QUOTE="\"";


    private final StorageService storageService;

    private final HttpServletRequest request;


    private UserEntityService userService;

    private StudyService studyService;

    private RoleService roleService;


    @PostMapping("upload")
    public Map<String, String > uploadFile(@RequestParam("file") MultipartFile multipartFile){
        String path = this.storageService.store(multipartFile);
//        String host = this.request.getRequestURL().toString().replace(this.request.getRequestURL(), "");
        String host = this.request.getRequestURL().toString();
//        System.out.println("host");
//        System.out.println(request.getClass());
//        System.out.println(this.request.getRequestURL());
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/file/")
                .path(path)
                .toUriString();

        url = url.replace("/upload/file", "");
        return Map.of("url", url);
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<?> getStudentsFile(@PathVariable String filename) throws IOException {
        Resource file = this.storageService.loadAsResource(filename);
//        Resource file = new ClassPathResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        System.out.println(contentType);
        System.out.println(file);
        System.out.println(filename);

        if(contentType.equals("text/csv")){
            File archive = null;
            FileReader fr = null;
            BufferedReader br = null;

            String linea = null;

            try {
                archive = new File(file.getFile().toURI());
                fr = new FileReader(archive);
                br = new BufferedReader(fr);
                List<CreateUserDTO> userList = new LinkedList<>();
                Set<String> roleSet = new HashSet<>();
                roleSet.add(ERole.USER_READER.name());



                String[] datos = null;
                int i = 0;
                linea = br.readLine();

                while ((linea) != null) {
                        // Dividir la línea en columnas utilizando una coma como separador (CSV)
                    String[] rowData = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // to take the comas, but not the comas that are inside quotes.

                    if(this.userService.checkIfExistByNif(Arrays.stream(rowData).toList().get(0))){ // if user exist simply, modify the attributes.
                        Optional<UserEntity> userToUpdate = this.userService.findByNif(Arrays.stream(rowData).toList().get(0)) ;

                        if(userToUpdate.isPresent()){
                            UserEntity user = userToUpdate.get();

                            Optional<Study> studySearched = this.studyService.findStudyByName(Arrays.stream(rowData).toList().get(11));
                            Study study = null;
                            if(studySearched.isPresent()){
                                study = studySearched.get();
                            }

                            user.setDniNie(Arrays.stream(rowData).toList().get(0));
                            user.setName(Arrays.stream(rowData).toList().get(1));
                            user.setSurname1(Arrays.stream(rowData).toList().get(2));
                            user.setSurname2(
                                    (Arrays.stream(rowData).toList().get(3)).isBlank() || Arrays.stream(rowData).toList().get(3).isEmpty()
                                    ? null :  Arrays.stream(rowData).toList().get(3) );
                            user.setMail(Arrays.stream(rowData).toList().get(4));
                            user.setPassword(Arrays.stream(rowData).toList().get(5));
                            user.setStatus(Arrays.stream(rowData).toList().get(6).equals("activo") ? true : false);
                            user.setDniNie(Arrays.stream(rowData).toList().get(10));
                            user.setStudy(study);
                            user.setCourse(Arrays.stream(rowData).toList().get(11));
                            user.setGroup(Arrays.stream(rowData).toList().get(12));
                            user.setRoles(this.roleService.findByRole(ERole.USER_READER));

                            this.userService.updateUser(user, user.getId());
                        }
                    }else{ // else, we will create this new user
                        System.out.println(Arrays.stream(rowData).toList().get(0));
                        System.out.println(rowData.length);
                        System.out.println(Arrays.toString(Arrays.stream(rowData).toArray()));

                        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                                .dni_nie(Arrays.stream(rowData).toList().get(0))
                                .name(Arrays.stream(rowData).toList().get(1))
                                .surname1(Arrays.stream(rowData).toList().get(2))
                                .surname2(
                                        (Arrays.stream(rowData).toList().get(3)).isBlank() || Arrays.stream(rowData).toList().get(3).isEmpty()
                                                ? null :  Arrays.stream(rowData).toList().get(3) )
                                .mail( Arrays.stream(rowData).toList().get(4))
                                .password( Arrays.stream(rowData).toList().get(5))
                                .status( Arrays.stream(rowData).toList().get(6))
                                .study( Arrays.stream(rowData).toList().get(10))
                                .course( Arrays.stream(rowData).toList().get(11))
                                .group( Arrays.stream(rowData).toList().get(12))
                                .roles(roleSet)
                                .build();

                        userList.add(createUserDTO);
                        this.userService.createUser(createUserDTO);
                    }

                    linea = br.readLine();

                }
                System.out.println(userList);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userList);

//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_TYPE, "text/csv")
//                        .body(userList);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fr != null) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                assert br != null;
                br.close();
            }
        }
        return ResponseEntity.notFound().build();
    }






}
