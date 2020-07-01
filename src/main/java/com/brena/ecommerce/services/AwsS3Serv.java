package com.brena.ecommerce.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, fileName, file);
            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
        } catch (IOException | AmazonServiceException ex) {
            LOGGER.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
    }

    @Async
    public void deleteFileFromS3Bucket(String fileName) {
        try {
            LOGGER.info("Deleting file with name= " + fileName);
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            LOGGER.info("File deleted successfully.");
        } catch (AmazonServiceException ex) {
            LOGGER.error("error occurred while removing= " + fileName);
        }
    }

    @Async
    public void deleteFile(String fileName) {
        LOGGER.info("Deleting file with name= " + fileName);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
        amazonS3.deleteObject(deleteObjectRequest);
        LOGGER.info("File deleted successfully.");
    }

    public void deleteFileFromBucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

}