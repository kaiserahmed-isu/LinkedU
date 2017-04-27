package com.linkedu.it353.service;

import com.linkedu.it353.model.UploadMaterials;

import java.util.List;

/**
 * Created by Kaiser on 4/21/2017.
 */
public interface UploadMaterialsService {
    public UploadMaterials findByUploadId(int uploadId);
    public List<UploadMaterials> findByUserId(int userId);
    public void saveUploadMaterials(UploadMaterials uploadMaterials);

}
