package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.Editorial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EditorialRepository extends CrudRepository<Editorial, Long> {

    Optional<Editorial> findByName(String name);
}
