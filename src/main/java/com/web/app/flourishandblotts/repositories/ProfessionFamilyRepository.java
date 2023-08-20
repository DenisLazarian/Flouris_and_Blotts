package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.ProfessionFamily;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionFamilyRepository extends CrudRepository<ProfessionFamily, Long> {

    Optional<ProfessionFamily> findByName(String name);

}
