package com.e_commerce.dao;

import com.e_commerce.Dto.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, String> {


    FileUpload findByUploadID(String docTypeId);
}
