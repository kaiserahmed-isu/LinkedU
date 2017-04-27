package com.linkedu.it353.service;

import com.linkedu.it353.model.Showcase;
import com.linkedu.it353.repository.ShowcaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
@Service
public class ShowcaseService {
    @Autowired
    private ShowcaseRepository showcaseRepository;

    public List<Showcase> getAllShowcased() {
        List<Showcase> showcases = new ArrayList<>();
        showcaseRepository.findAll().forEach(showcases::add);
        return showcases;
    }

    public void addToShowcase(Showcase showcase) {
        showcaseRepository.save(showcase);
    }

    public void updateFromShowcase(Integer showcase_id, Showcase showcase) {
        showcaseRepository.save(showcase);
    }

public void removeShowcase(Integer showcase_id){
                showcaseRepository.delete(showcase_id);
}
}
