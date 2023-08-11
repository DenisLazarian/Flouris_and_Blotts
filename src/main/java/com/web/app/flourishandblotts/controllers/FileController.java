package com.web.app.flourishandblotts.controllers;

import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.services.StorageService;
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
    public ResponseEntity<?> getFile(@PathVariable String filename) throws IOException {
        Resource file = this.storageService.loadAsResource(filename);
//        Resource file = new ClassPathResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        System.out.println(contentType);
        System.out.println(file);
        System.out.println(filename);

        if(contentType.equals("text/csv")){
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            String linea = null;

            try {
                archivo = new File(file.getFile().toURI());
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                List<String[]> userList = new LinkedList<>();
//                List<String[]> csvData = new ArrayList<>();

                String[] datos = null;
                int i = 0;
                linea = br.readLine();
                //Leemos hasta que se termine el archivo
                while ((linea) != null) {
                        // Dividir la línea en columnas utilizando una coma como separador (CSV)
                    String[] rowData = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // to take the comas, but not the comas that are inside quotes.

                    System.out.println(Arrays.stream(rowData).toList().get(0));
                    System.out.println(rowData.length);

                    System.out.println(Arrays.toString(Arrays.stream(rowData).toArray()));

//                    userList.add(rowData);
                    linea = br.readLine();

                }
//                System.out.println(userList);
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(userList);
                //Capturamos las posibles excepciones
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
            }
        }
        return ResponseEntity.notFound().build();
    }

    private String[] removeTrailingQuotes(String[] fields) {
        String[] result = new String[fields.length];

        for (int i=0;i<result.length;i++){
            result[i] = fields[i].replaceAll("^"+QUOTE, "").replaceAll(QUOTE+"$", "");
        }
        return result;
    }


}
