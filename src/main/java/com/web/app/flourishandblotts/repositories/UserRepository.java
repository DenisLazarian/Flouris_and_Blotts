package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByMail(String mail);

    @Query("select u from UserEntity u where u.id = ?1")
    Optional<UserEntity> getById(Long id);

    @Query("select u from UserEntity u where u.mail = '?1'")
    Optional<UserEntity> getUsersByMail(String mail);

    @Query("select u from UserEntity u")
    List<UserEntity> getList();

    boolean findByDniNie(String dniNie);
    @Query("select u from UserEntity u where u.dniNie = '?1'")
    Optional<UserEntity> getByNif(String nif);
}
