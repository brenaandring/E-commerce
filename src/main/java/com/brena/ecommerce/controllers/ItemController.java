package com.brena.ecommerce.controllers;

import com.brena.ecommerce.models.*;
import com.brena.ecommerce.services.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import java.util.Objects;
import java.util.UUID;

@Controller
public class ItemController {
    private final ItemServ itemServ;
    private final ReviewServ reviewServ;
    private final PhotoServ photoServ;

    public ItemController(ItemServ itemServ,
                          ReviewServ reviewServ,
                          PhotoServ photoServ) {
        this.itemServ = itemServ;
        this.reviewServ = reviewServ;
        this.photoServ = photoServ;
    }

    //  admin-only: create a new item
    @GetMapping("/admin/items/create")
    public ModelAndView createItem(ModelAndView modelAndView) {
        modelAndView.setViewName("create");
        modelAndView.addObject("item", new Item());
        return modelAndView;
    }

    @PostMapping("/admin/items/create")
    public ModelAndView create(@Valid Item item,
                         BindingResult result,
                         @RequestParam("imageFile") MultipartFile imageFile, ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName("create");
        } else {
            itemServ.saveItem(item);
            Photo newPhoto = new Photo();
            String generatedFilename = UUID.randomUUID().toString() + getFileExtension(Objects.requireNonNull(imageFile.getOriginalFilename()));
            newPhoto.setFileName(generatedFilename);
            newPhoto.setItem(item);
            try {
                photoServ.savePhotoImage(imageFile, generatedFilename);
                photoServ.savePhoto(newPhoto);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ModelAndView("redirect:/admin");
        }
        return modelAndView;
    }

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
        modelAndView.addObject("item", itemServ.findItem(id));
        modelAndView.addObject("review", new Review());
        modelAndView.addObject("photo", photoServ.findById(id));
        modelAndView.addObject("reviewer", reviewServ.findById(id));
        return modelAndView;
    }

    //  admin-only: edit an item
    @GetMapping("/admin/items/edit/{id}")
    public ModelAndView editItem(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("update");
        modelAndView.addObject("item", itemServ.findItem(id));
        return modelAndView;
    }

    @PostMapping("/admin/items/edit/{id}")
    public ModelAndView update(@Valid Item item,
                         BindingResult result,
                         @RequestParam("imageFile") MultipartFile imageFile, ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName("update");
        } else {
            itemServ.saveItem(item);
            Photo photo = photoServ.findByItem(item);
            if (photo == null) {
                Photo newPhoto = new Photo();
                String generatedFilename = UUID.randomUUID().toString() + getFileExtension(imageFile.getOriginalFilename());
                newPhoto.setFileName(generatedFilename);
                newPhoto.setItem(item);
                try {
                    photoServ.savePhotoImage(imageFile, generatedFilename);
                    photoServ.savePhoto(newPhoto);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return new ModelAndView("redirect:/admin");
        }
        return modelAndView;
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
        return "redirect:/admin";
    }

    //  admin-only: delete a photo
    @RequestMapping("/admin/photo/delete/{id}")
    public String deletePhoto(@PathVariable("id") Long id) {
        photoServ.deletePhoto(id);
        return "redirect:/admin";
    }

    //  admin-only: delete an item
    @RequestMapping("/admin/items/delete/{id}")
    public String destroy(@PathVariable("id") Long id) {
        itemServ.deleteItem(id);
        return "redirect:/admin";
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return filename.substring(lastIndexOf);
    }
}
