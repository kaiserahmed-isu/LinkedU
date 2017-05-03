package com.linkedu.it353.service;


import com.linkedu.it353.model.UniversityProfile;
import com.linkedu.it353.repository.UniversityProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
@Service
public class UniversityProfileService {
    @Autowired
    private UniversityProfileRepository universityProfileRepository;

    public List<UniversityProfile> getAllUniversities() {
        List<UniversityProfile> universityProfiles = new ArrayList<>();
        universityProfileRepository.findAll().forEach(universityProfiles::add);
        return universityProfiles;
    }

    public UniversityProfile getUniversity(Integer user_id) {
        return universityProfileRepository.findOne(user_id);
    }

    public void addUniversity(UniversityProfile universityProfile) {
        universityProfileRepository.save(universityProfile);
    }

    public void updateUniversity(Integer user_id, UniversityProfile universityProfile) {
        universityProfileRepository.save(universityProfile);
    }

    public List<UniversityProfile> searchProfile(UniversityProfile universityProfile) {
        List<UniversityProfile> universityProfiles = new ArrayList<>();
        return universityProfileRepository.findByUniversityNameAndCityAndStateAndZipIgnoreCase(universityProfile.getUniversityName(), universityProfile.getCity(), universityProfile.getState(), universityProfile.getZip());
    }
}