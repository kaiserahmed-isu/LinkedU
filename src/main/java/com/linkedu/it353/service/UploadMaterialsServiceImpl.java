package com.linkedu.it353.service;

import com.linkedu.it353.model.UploadMaterials;
import com.linkedu.it353.repository.UploadMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Service("uploadMaterialsService")
public class UploadMaterialsServiceImpl implements UploadMaterialsService {

    @Autowired
    UploadMaterialsRepository uploadMaterialsRepository;

    @Override
    public UploadMaterials findByUploadId(int uploadId){
        return uploadMaterialsRepository.findByUploadId(uploadId);
    }

    @Override
    public void saveUploadMaterials(UploadMaterials uploadMaterials){
        uploadMaterialsRepository.save(uploadMaterials);
    }
}
