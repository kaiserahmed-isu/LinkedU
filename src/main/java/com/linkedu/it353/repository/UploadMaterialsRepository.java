package com.linkedu.it353.repository;

/**
 * Created by Kaiser on 4/21/2017.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkedu.it353.model.UploadMaterials;

import java.util.List;

@Repository("uploadMaterialsRepository")
public interface UploadMaterialsRepository extends JpaRepository<UploadMaterials, Long> {
    UploadMaterials findByUploadId(int uploadId);

    List<UploadMaterials> findByUserId(int userId);
}