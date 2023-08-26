package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

}
