package com.e_commerce.services.impl;
import com.e_commerce.Dto.FileUpload;
import com.e_commerce._util.ConverterUtils;
import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.FileUploadRepository;
import com.e_commerce.exception.SDDException;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.UplaodMainFormDocumentsResponse;
import com.e_commerce.services.UploadDocumentService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@AllArgsConstructor
public class UploadDocumentServiceImpl implements UploadDocumentService {

    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse<UplaodMainFormDocumentsResponse> fileUplaod(MultipartFile file) throws IOException {


        UplaodMainFormDocumentsResponse uplaodDocuments = new UplaodMainFormDocumentsResponse();

        File mainFilePath = new File(HelperUtils.LASTFOLDERPATH);

        if (!mainFilePath.exists()) {
            mainFilePath.mkdirs();
        }
        if (file == null || file.isEmpty()) {
            throw new SDDException(HttpStatus.BAD_REQUEST.value(), "DOCUMENT FILE CAN NOT BE BLANK.");
        }
        String fileExtension1 = getFileExtension(file);
        if (fileExtension1.equalsIgnoreCase(".jpg") || fileExtension1.equalsIgnoreCase(".jpeg")) {

        } else {
            throw new SDDException(HttpStatus.BAD_REQUEST.value(), "FILE NOT SUPPORTED ");
        }

        String filename1 = ConverterUtils.getRandomString("");
        File mainPath1 = ConverterUtils.getComplaintPathOnly(fileExtension1, filename1, mainFilePath.getAbsolutePath());
        Path path1 = Paths.get(mainPath1.toString());
        InputStream in1 = new ByteArrayInputStream(file.getBytes());

        try {
            System.out.println(
                    "Number of bytes copied1: "
                            + Files.copy(in1, path1.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING));

        } catch (IOException e) {
            e.printStackTrace();
        }

        FileUpload fileUpload = new FileUpload();
        fileUpload.setUploadID(HelperUtils.getDocumentId());
        fileUpload.setPathURL(HelperUtils.FILEPATH + filename1 + fileExtension1);
        fileUpload.setPathURL(filename1 + fileExtension1);
        fileUploadRepository.save(fileUpload);

        uplaodDocuments.setUploadDocId(fileUpload.getUploadID());
//        uplaodDocuments.setUploadPathUrl(HelperUtils.FILEPATH + filename1 + fileExtension1);
        uplaodDocuments.setUploadPathUrl(filename1 + fileExtension1);
        uplaodDocuments.setMessage("File upload successfully");

        return ResponseUtils.createSuccessResponse(uplaodDocuments, new TypeReference<UplaodMainFormDocumentsResponse>() {
        });


    }
    public String getFileExtension(MultipartFile file) {
        String fileExtention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        return fileExtention;
    }
}


