package com.brena.ecommerce.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AwsS3Serv {
    private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3Serv.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Async
    public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess) {
        String fileName = multipartFile.getOriginalFilename();
        try {
            Path tempDirectory = Files.createTempDirectory("item-image");
            File tempFile = new File(tempDirectory.toFile(), fileName);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, fileName, tempFile);
            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            tempFile.delete();
        } catch (IOException | AmazonServiceException ex) {
            LOGGER.error("error occurred while uploading file", ex);
        }
    }
}