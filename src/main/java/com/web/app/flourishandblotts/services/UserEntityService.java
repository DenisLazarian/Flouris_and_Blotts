package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.controllers.request.CreateUserDTO;
import com.web.app.flourishandblotts.models.ERole;
import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.models.Study;
import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.RoleRepository;
import com.web.app.flourishandblotts.repositories.StudyRepository;
import com.web.app.flourishandblotts.repositories.UserRepository;
import jakarta.annotation.Resource;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserEntityService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private StudyRepository studyRepository;


    public UserEntity createUser(CreateUserDTO createUserDTO){
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(roleName -> {
                    ERole role = ERole.valueOf(roleName);
                    return this.roleRepository.findByName(role).orElseGet(() -> {
                        RoleEntity newRole = RoleEntity.builder().name(role).build();
                        return this.roleRepository.save(newRole);
                    });
                })
                .collect(Collectors.toSet());

        Study study = this.studyRepository.findStudyByName(createUserDTO.getStudy()).orElseGet(()->{
                        Study newStudy = Study.builder().name(createUserDTO.getStudy()).build();
                        return this.studyRepository.save(newStudy);
                    });

        UserEntity userEntity = UserEntity.builder()
                .dniNie(createUserDTO.getDni_nie())
                .password(this.passwordEncoder.encode(createUserDTO.getPassword()))
                .name(createUserDTO.getName())
                .surname1(createUserDTO.getSurname1())
                .surname2(createUserDTO.getSurname2())
                .mail(createUserDTO.getMail())
                .status(Boolean.parseBoolean(createUserDTO.getStatus()))
                .lastModifiedDate(new Date(new java.util.Date().getTime()))
                .roles(roles)
                .penalization(null)
                .study(study)
                .build();

        this.userRepository.save(userEntity);
        return userEntity;
    }

    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }

    public Optional<UserEntity> findById(Long id){
        return this.userRepository.findById(id);
    }

    public UserEntity updateUser(UserEntity request, Long id){
        Optional<UserEntity> userOptional = this.userRepository.getById(id);
        if(userOptional.isEmpty()) return null;

        UserEntity user = userOptional.get();

        return this.makeSetters(user, request);
    }

    public List<UserEntity> list(){
        if(!this.userRepository.getList().isEmpty())
            return this.userRepository.getList() ;
        else return Collections.emptyList();
    }

    public UserEntity makeSetters(UserEntity u, UserEntity request){
        u.setMail(request.getMail());
        u.setDniNie(request.getDniNie());
        u.setName(request.getName());
        u.setStatus(request.isStatus());
        if(!(request.getPassword().isEmpty() || request.getPassword().isBlank())){
            u.setPassword(this.passwordEncoder.encode(request.getPassword()));
        }
        u.setRoles(u.getRoles());
        u.setStudy(request.getStudy());
//        u.getPenalization()
        u.setGroup(request.getGroup());
        u.setCourse(request.getCourse());
        u.setLastModifiedDate(new Date(new java.util.Date().getTime()));
        u.setSurname1(request.getSurname1());
        u.setRoles(request.getRoles());

        this.userRepository.save(u);
        return u;
    }

    public List<UserEntity> createOrSaveFromCSV(){

        return null;
    }

    public boolean checkIfExistByNif(String nif){
        return this.userRepository.findByDniNie(nif);
    }


    public Optional<UserEntity> findByNif(String nif){
        return Optional.ofNullable(this.userRepository.getByNif(nif).orElseThrow(
                () -> new UsernameNotFoundException("the user with nif " + nif + " isn`t registered in data base."))
        );
    }




}
