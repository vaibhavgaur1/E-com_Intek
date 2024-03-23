package com.e_commerce.services;


import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.UplaodMainFormDocumentsResponse;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UploadDocumentService {

	ApiResponse<UplaodMainFormDocumentsResponse> fileUpload(MultipartFile file) throws IOException;;





}
