package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.controllers.request.CreateBookDTO;
import com.web.app.flourishandblotts.models.*;
import com.web.app.flourishandblotts.repositories.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {


    @Resource
    private BookRepository bookRepository;

    @Resource
    private LanguageRepository languageRepository;

    @Resource
    private EditorialRepository editorialRepository;
    @Resource
    private AuthorRepository authorRepository;


    @Resource
    private CategoryRepository categoriesRepository;


    public BookEntity createBook(CreateBookDTO createBookDTO){

        if(this.languageRepository.findByName(createBookDTO.getLanguage()).isEmpty()){
            Language language = new Language();
            language.setName(createBookDTO.getLanguage());

            this.languageRepository.save(language);
        }

        Language lang = this.languageRepository.findByName(createBookDTO.getLanguage()).get();

        if(this.editorialRepository.findByName(createBookDTO.getEditorial()).isEmpty()){
            Editorial ed = new Editorial();
            ed.setName(createBookDTO.getEditorial());
            this.editorialRepository.save(ed);
        }

        Editorial editorial = this.editorialRepository.findByName(createBookDTO.getEditorial()).get();


        Set<Author> authorToSet = new HashSet<>();

        for(String author: createBookDTO.getAuthors()){
            if(this.authorRepository.findByName(author).isEmpty()){
                Author auth = new Author();
                auth.setName(author);
                this.authorRepository.save(auth);
            }

            Author a = this.authorRepository.findByName(author).get();
            authorToSet.add(a);
        }

        Set<Category> categoriesToSet = new HashSet<>();

        if(createBookDTO.getCategories() != null){
            for(String category: createBookDTO.getCategories()){
                if(this.categoriesRepository.findByName(category).isEmpty()){
                    Category cat = new Category();
                    cat.setName(category);
                    this.categoriesRepository.save(cat);
                }

                Category searchedCat = this.categoriesRepository.findByName(category).get();
                categoriesToSet.add(searchedCat);
            }
        }


        BookEntity book = BookEntity.builder()
                .isbn_13(createBookDTO.getIsbn_13())
                .title(createBookDTO.getTitle())
                .subtitle(createBookDTO.getSubtitle())
                .datePublished(createBookDTO.getDatePublished())
                .pageNumber(createBookDTO.getPageNumber())
                .description(createBookDTO.getDescription())
                .thumbnail(createBookDTO.getThumbnail())
                .language(lang)
                .editorial(editorial)
                .authors(authorToSet)
                .categories(categoriesToSet)
                .build();

        this.bookRepository.save(book);
        return book;
    }

    public List<BookEntity> listBooks(){
        return this.bookRepository.listBooks();
    }

    public BookEntity findTitleRepeated(String title) {
        return this.bookRepository.getBookEntitiesByTitle(title);
    }
}
