package com.linkedu.it353.model;

import javax.persistence.*;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Entity
@Table(name = "upload_materials")
public class UploadMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "upload_id")
    private int uploadId;


    @Column(name = "user_id")
    private int userId;

    private String type;


    @Column(name="file_name")
    private String fileName;



    @Column(name="file_type")
    private String fileType;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


}
