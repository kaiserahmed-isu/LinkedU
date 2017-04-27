package com.linkedu.it353.service;


import com.linkedu.it353.model.UniversityProgram;
import com.linkedu.it353.repository.UniversityProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
@Service
public class UniversityProgramService {
    @Autowired
    private UniversityProgramRepository universityProgramRepository;

    public List<UniversityProgram> getAllPrograms() {
        List<UniversityProgram> universityPrograms = new ArrayList<>();
        universityProgramRepository.findAll().forEach(universityPrograms::add);
        return universityPrograms;
    }

    public UniversityProgram getProgram(Integer program_id) {
        return universityProgramRepository.findOne(program_id);
    }

    public void addProgram(UniversityProgram universityProgram) {
        universityProgramRepository.save(universityProgram);
    }

    public void updateProgram(Integer program_id, UniversityProgram universityProgram) {
        universityProgramRepository.save(universityProgram);
    }
}
