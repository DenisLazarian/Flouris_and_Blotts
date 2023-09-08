package com.web.app.flourishandblotts;

import com.web.app.flourishandblotts.controllers.request.CreateBookDTO;
import com.web.app.flourishandblotts.models.ERole;
import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.UserRepository;
import com.web.app.flourishandblotts.services.BookService;
import jakarta.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SpringBootApplication
public class App {

    private final Environment environment;

    @Autowired
    public App(Environment environment) {
        this.environment = environment;
    }

    @Resource
    UserRepository userRepository;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner init(){
        return args -> {
            String environmentStatus = environment.getProperty("spring.jpa.hibernate.ddl-auto");
            if(
                    Objects.equals(environmentStatus, "create-drop") ||
                    Objects.equals(environmentStatus, "create")
            ){
                this.creatingUsers();


                    this.getApiBooks();

            }

        };
    }

    private void getApiBooks() throws IOException {

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=subject:Mystery+adventure&maxResults=40");
        HttpURLConnection cx = (HttpURLConnection) url.openConnection();

        cx.setRequestMethod("GET");
        InputStream strm = cx.getInputStream();
        byte[] arrStream = strm.readAllBytes();

        StringBuilder cntJson= new StringBuilder();

        for(byte tmp: arrStream)
            cntJson.append((char) tmp);


        Object items = new JSONObject(cntJson.toString()).get("items");

//        System.out.println(cntJson);
        JSONArray json = new JSONArray(items.toString());
        for(Object obj: json){

//            System.out.println(((JSONObject)obj).get("id"));
//            System.out.println(((JSONObject) obj).get("volumeInfo"));

            JSONObject volumeInfo = new JSONObject(((JSONObject) obj).get("volumeInfo").toString());
//            JSONArray setVolumeInfo = new JSONArray((volumeInfo.toString()));

            String title = volumeInfo.get("title").toString();


            String subtitle = volumeInfo.has("subtitle") ?
                    volumeInfo.get("subtitle").toString() : null;

            String datePublished = volumeInfo.get("publishedDate").toString();
            String pageNumber = volumeInfo.has("pageCount") ?
                    volumeInfo.get("pageCount").toString() : null;
            String description = volumeInfo.has("description") ?
                    volumeInfo.get("description").toString() : null;

            String thumbnail = null;

            if(volumeInfo.has("imageLinks")){
                JSONObject thumbnailSet = new JSONObject(volumeInfo.get("imageLinks").toString());
                thumbnail = thumbnailSet.get("thumbnail").toString();
            }

            String editorial = volumeInfo.has("publisher") ?
                    volumeInfo.get("publisher").toString(): null;

            String language = volumeInfo.has("language") ?
                    volumeInfo.get("language").toString() : null;


            Set<String> categories = null;

            if(volumeInfo.has("categories")){
                categories = new HashSet<>();
                JSONArray catList = new JSONArray(volumeInfo.get("categories").toString());
                for(Object category: catList)
                    categories.add(category.toString());
            }

            Set<String> authors = null;

            if(volumeInfo.has("authors")){
                authors = new HashSet<>();
                JSONArray authorsList = new JSONArray(volumeInfo.get("authors").toString());
                for (Object author: authorsList)
                    authors.add(author.toString());
            }

            JSONArray industryIdentifiers = new JSONArray(volumeInfo.get("industryIdentifiers").toString());
            String isbn_13 = null;

            for(Object ident : industryIdentifiers){
                JSONObject it = new JSONObject(ident.toString());

                if(it.has("type") &&
                        it.get("type").toString().equalsIgnoreCase("ISBN_13")){
                    isbn_13 = it.get("identifier").toString();
                }
            }
            if(isbn_13 != null && bookService.findTitleRepeated(title) == null){

                CreateBookDTO createBookDTO = CreateBookDTO.builder()
                        .isbn_13(isbn_13)
                        .title(title)
                        .subtitle(subtitle)
                        .datePublished(datePublished)
                        .pageNumber(pageNumber!= null ? Integer.parseInt(pageNumber): 0)
                        .description(description)
                        .thumbnail(thumbnail)
                        .language(language)
                        .editorial(editorial)
                        .categories(categories)
                        .authors(authors)
                .build();

                this.bookService.createBook(createBookDTO);
            }
        }
    }

    public void creatingUsers(){


        UserEntity userEntity1 = UserEntity.builder()
                .mail("user@me.local")
                .name("user")
                .dniNie("0000003")
                .surname1("student")
                .surname2(null)
                .password(passwordEncoder.encode("1234"))
                .status(true)
                .lastModifiedDate(new java.sql.Date(new java.util.Date().getTime()))
                .course("2")
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
                .status(true)
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
                .status(true)
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
