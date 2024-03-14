package com.e_commerce.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "FileUpload")
public class FileUpload {

    @Id
    @Column(name = "UPLOAD_ID")
    private String uploadID;

    @Column(name = "PATH_URL")
    private String pathURL;




}
