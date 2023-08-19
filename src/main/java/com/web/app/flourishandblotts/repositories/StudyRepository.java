package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.Study;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.Style;
import java.util.Optional;

@Repository
public interface StudyRepository extends CrudRepository<Study, Long> {

    @Query("select s from Study s where s.name = '?1'")
    Optional<Study> findStudyByName(String name);

    @Query("select s from Study s")
    Optional<Study> listStudies();


}
