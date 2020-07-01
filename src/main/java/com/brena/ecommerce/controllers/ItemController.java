package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
public class ItemController {
    private final ItemServ itemServ;
    private final ReviewServ reviewServ;
    private final PhotoServ photoServ;
    private final CategoryServ categoryServ;
    private final AwsS3Serv awsS3Serv;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public ItemController(ItemServ itemServ,
                          ReviewServ reviewServ,
                          PhotoServ photoServ,
                          CategoryServ categoryServ,
                          AwsS3Serv awsS3Serv) {
        this.itemServ = itemServ;
        this.reviewServ = reviewServ;
        this.photoServ = photoServ;
        this.categoryServ = categoryServ;
        this.awsS3Serv = awsS3Serv;
    }

//    @PostMapping(value= "/upload")
//    public ResponseEntity<String> uploadFile(@RequestPart(value= "file") final MultipartFile multipartFile) {
//        awsS3Serv.uploadFile(multipartFile);
//        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @PostMapping("/upload")
//    public ModelAndView uploadFile(@RequestPart(value= "file") MultipartFile multipartFile,
//                                   ModelAndView modelAndView,
//                                   BindingResult result) {
//        if (result.hasErrors()) {
//            modelAndView.setViewName("create");
//        } else {
//            awsS3Serv.uploadFile(multipartFile);
//            return new ModelAndView("redirect:/admin/items");
//        }
//        return modelAndView;
//    }





    //  admin-only: create a new item
    @GetMapping("/admin/items/create")
    public ModelAndView createItem(ModelAndView modelAndView) {
        modelAndView.setViewName("create");
        modelAndView.addObject("item", new Item());
        modelAndView.addObject("categories", categoryServ.allCategories());
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/admin/items/create")
    public ModelAndView create(@Valid Item item,
                               Category category,
                               BindingResult result,
                               @RequestPart("imageFile") MultipartFile imageFile,
                               ModelAndView modelAndView, HttpServletRequest request) {
        if (result.hasErrors()) {
            modelAndView.setViewName("create");
        } else {
            HttpSession session = request.getSession();
            User admin = (User) session.getAttribute("admin");
            if (category != null && category.getId() != null) {
                item.setCategory(category);
            }
            if (!Objects.requireNonNull(imageFile.getOriginalFilename()).isEmpty()) {
                awsS3Serv.uploadFileToS3Bucket(imageFile, true);
                String filename = "https://" + bucketName + ".s3-" + region + ".amazonaws.com/" + imageFile.getOriginalFilename();
                item.setImage(filename);
            }
            item.setUser(admin);
            itemServ.saveItem(item);
            return new ModelAndView("redirect:/admin/items");
        }
        return modelAndView;
    }

//    @PostMapping("/admin/items/create")
//    public ModelAndView create(@Valid Item item,
//                               Category category,
//                               BindingResult result,
//                               @RequestParam("imageFile") MultipartFile imageFile,
//                               ModelAndView modelAndView, HttpServletRequest request) {
//        if (result.hasErrors()) {
//            modelAndView.setViewName("create");
//        } else {
//            HttpSession session = request.getSession();
//            User admin = (User) session.getAttribute("admin");
//            if (category != null && category.getId() != null) {
//                item.setCategory(category);
//            }
//            item.setUser(admin);
//            itemServ.saveItem(item);
//            Photo newPhoto = new Photo();
//            String generatedFilename = UUID.randomUUID().toString() + getFileExtension(Objects.requireNonNull(imageFile.getOriginalFilename()));
//            newPhoto.setFileName(generatedFilename);
//            newPhoto.setItem(item);
//            try {
//                photoServ.savePhotoImage(imageFile, generatedFilename);
//                photoServ.savePhoto(newPhoto);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return new ModelAndView("redirect:/admin/items");
//        }
//        return modelAndView;
//    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = photoServ.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    //  show an item
    @GetMapping("/items/{id}")
    public ModelAndView showItem(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("read");
        Item item = itemServ.findItem(id);

//        String currentImagePath = item.getImage();
//        String defaultImagePath = "https://ecomimgbucket.s3-us-west-1.amazonaws.com/Placeholder.png";
//        boolean defaultImage = (currentImagePath.equals(defaultImagePath));

        modelAndView.addObject("item", item);
        modelAndView.addObject("review", new Review());
//        modelAndView.addObject("defaultImage", defaultImage);
        modelAndView.addObject("reviewer", reviewServ.findById(id));
        return modelAndView;
    }

    //  admin-only: edit an item
    @GetMapping("/admin/items/edit/{id}")
    public ModelAndView editItem(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("update");
        modelAndView.addObject("item", itemServ.findItem(id));
        modelAndView.addObject("categories", categoryServ.allCategories());
        return modelAndView;
    }

    @PostMapping("/admin/items/edit/{id}")
    public ModelAndView update(@Valid Item item,
                         BindingResult result,
                         @RequestParam("imageFile") MultipartFile imageFile,
                               HttpServletRequest request, ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName("update");
        } else {
            HttpSession session = request.getSession();
            User admin = (User) session.getAttribute("admin");
            if (!Objects.requireNonNull(imageFile.getOriginalFilename()).isEmpty()) {
                awsS3Serv.uploadFileToS3Bucket(imageFile, true);
                String filename = "https://" + bucketName + ".s3-" + region + ".amazonaws.com/" + imageFile.getOriginalFilename();
                item.setImage(filename);
            }
            item.setUser(admin);
            itemServ.saveItem(item);
            return new ModelAndView("redirect:/admin/items");
        }
        return modelAndView;
    }

//    @PostMapping("/admin/items/edit/{id}")
//    public ModelAndView update(@Valid Item item,
//                               BindingResult result,
//                               @RequestParam("imageFile") MultipartFile imageFile,
//                               HttpServletRequest request, ModelAndView modelAndView) {
//        if (result.hasErrors()) {
//            modelAndView.setViewName("update");
//        } else {
//            HttpSession session = request.getSession();
//            User admin = (User) session.getAttribute("admin");
//            item.setUser(admin);
//            itemServ.saveItem(item);
//            Photo photo = photoServ.findByItem(item);
//            if (photo == null) {
//                Photo newPhoto = new Photo();
//                String generatedFilename = UUID.randomUUID().toString() + getFileExtension(imageFile.getOriginalFilename());
//                newPhoto.setFileName(generatedFilename);
//                newPhoto.setItem(item);
//                try {
//                    photoServ.savePhotoImage(imageFile, generatedFilename);
//                    photoServ.savePhoto(newPhoto);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//            return new ModelAndView("redirect:/admin/items");
//        }
//        return modelAndView;
//    }

    // admin-only: create category
    @PostMapping("/admin/category/create")
    public ModelAndView createCategory(@Valid Category category,
                                       BindingResult result,
                                       ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName("create");
        } else {
            categoryServ.saveCategory(category);
        }
        return new ModelAndView("redirect:/admin/items/create");
    }

    //  user: create an item review
    @PostMapping("/user/items/review/{id}")
    public RedirectView createReview(@PathVariable("id") Long id,
                                     @Valid Review review,
                                     BindingResult result,
                                     HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        RedirectView redirectView = new RedirectView("/items/{id}");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please write a review.");
        } else {
            reviewServ.saveReview(review, id, user);
        }
        return redirectView;
    }

    //  user: delete their review
    @RequestMapping("/user/review/delete/{id}")
    public String destroyReview(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Review reviewToDelete = reviewServ.findById(id);
        if (reviewToDelete != null) {
            if (reviewToDelete.getUser().getId().equals(user.getId())) {
                reviewServ.deleteReview(id);
            } else {
                System.out.println("Somebody is doing something nasty");
            }
        }
        return "redirect:/user/dashboard";
    }

    //  admin-only: delete a review
    @RequestMapping("/admin/review/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewServ.deleteReview(id);
        return "redirect:/admin/items";
    }

    @RequestMapping("/admin/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryServ.deleteCategory(id);
        return "redirect:/admin/items/create";
    }

    //  admin-only: delete a photo
//    @RequestMapping("/admin/photo/delete/{id}")
//    public String deletePhoto(@PathVariable("id") Long id) {
//        photoServ.deletePhoto(id);
//        return "redirect:/admin/items";
//    }

    @RequestMapping("/admin/photo/delete")
    public String deleteFile(@RequestParam(value= "fileName") String fileName) {
        awsS3Serv.deleteFile(fileName);
        return "redirect:/admin/items";
    }

    //  admin-only: delete an item
    @RequestMapping("/admin/items/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        itemServ.deleteItem(id);
        return "redirect:/admin/items";
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return filename.substring(lastIndexOf);
    }
}
