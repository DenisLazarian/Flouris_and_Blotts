package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {

    Optional<Language> findByName(String name);
}
