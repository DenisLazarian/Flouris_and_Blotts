package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.models.ERole;
import com.web.app.flourishandblotts.models.RoleEntity;
import com.web.app.flourishandblotts.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public Set<RoleEntity> findByRole(ERole name){

        Set<RoleEntity> roles = new HashSet<>();

        if(this.roleRepository.findByName(name).isEmpty()) return null;

        roles.add(this.roleRepository.findByName(name).get());
        return roles;
    }
}
