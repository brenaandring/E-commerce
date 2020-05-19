package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Photo;
import com.brena.ecommerce.repositories.PhotoRepo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
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

    public void savePhotoImage(MultipartFile imageFile, String filename) throws Exception {
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(".", "images", filename);
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

    public Resource loadAsResource(String filename) {
        try {
            Path rootLocation = Paths.get(".", "images");
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new IllegalStateException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Could not read file: " + filename, e);
        }
    }
}
