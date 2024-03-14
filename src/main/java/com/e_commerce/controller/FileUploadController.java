package com.e_commerce.controller;


import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.UplaodMainFormDocumentsResponse;
import com.e_commerce.services.UploadDocumentService;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/fileUpload")
@Slf4j
public class FileUploadController {

	@Autowired    
	private UploadDocumentService uploadDocumentService;



	@PostMapping(value = "/uploadFile")
	public ResponseEntity<ApiResponse<UplaodMainFormDocumentsResponse>> uploadPhotoRegistrationApi(@FormDataParam("file") MultipartFile file) throws IOException {
		return new ResponseEntity<>(uploadDocumentService.fileUplaod(file), HttpStatus.OK);
	}

//	@CrossOrigin(origins = "http://localhost:1111")









}
