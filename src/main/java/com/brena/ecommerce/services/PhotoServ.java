package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Photo;
import com.brena.ecommerce.repositories.PhotoRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoServ {
    public final PhotoRepo photoRepo;

    public PhotoServ(PhotoRepo photoRepo) {
        this.photoRepo = photoRepo;
    }

    //  find all photos
    public List<Photo> findAll() {
        return photoRepo.findAll();
    }

    //  create photo
    public void savePhoto(Photo photo) {
        photoRepo.save(photo);
    }

    public void savePhotoImage(MultipartFile imageFile, Photo photo) throws Exception {
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        photo.setPath(absolutePath + "/src/main/resources/static/photos/");
        byte[] bytes =  imageFile.getBytes();
        Path path = Paths.get(photo.getPath() + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }

    //  find by id
    public Photo findById(Long id) {
        Optional<Photo> photo = photoRepo.findById(id);
        return photo.orElse(null);
    }

    //  delete photo
    public void deletePhoto(Long id) {
        photoRepo.deleteById(id);
    }

}
