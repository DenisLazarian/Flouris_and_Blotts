package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.models.Study;
import com.web.app.flourishandblotts.repositories.StudyRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyService {

    @Resource
    private StudyRepository studyRepository;

    public Optional<Study> findStudyByName(String name){
        return this.studyRepository.findStudyByName(name);
    }
}
