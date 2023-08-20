package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.models.ProfessionFamily;
import com.web.app.flourishandblotts.repositories.ProfessionFamilyRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class ProfessionFamilyService {

    @Resource
    private ProfessionFamilyRepository professionFamilyRepository;

    public ProfessionFamily findByName(String name){
        if(this.professionFamilyRepository.findByName(name).isEmpty())
            return null;

        return this.professionFamilyRepository.findByName(name).get();
    }
}
