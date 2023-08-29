package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    BookEntity getBookEntitiesByTitle(String title);

    @Query("select b from BookEntity b")
    Optional<List<BookEntity>> listBooks();

}
