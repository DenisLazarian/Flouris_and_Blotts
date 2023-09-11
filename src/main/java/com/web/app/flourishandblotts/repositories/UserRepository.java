package com.web.app.flourishandblotts.repositories;

import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByMail(String mail);

    @Query("select u from UserEntity u where u.id = ?1")
    Optional<UserEntity> getById(Long id);

    @Query("select u from UserEntity u where u.mail = '?1'")
    Optional<UserEntity> getUsersByMail(String mail);

    @Query("select u from UserEntity u")
    List<UserEntity> getList();

    Optional<UserEntity> findByDniNie(String dniNie);
    @Query("select u from UserEntity u where u.dniNie = :nif")
    Optional<UserEntity> getByNif(@Param("nif") String nif);

    @Query("select u.roles from UserEntity u inner join u.roles r where u.mail = :mail")
    Optional<Set<RoleEntity>> checkRole(String mail);
}
