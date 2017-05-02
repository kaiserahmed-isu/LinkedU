package com.linkedu.it353.repository;

import com.linkedu.it353.model.Showcase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
public interface ShowcaseRepository extends CrudRepository<Showcase, Integer> {
    Showcase findFirstByPriority(Integer priority );
}
