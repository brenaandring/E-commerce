package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.Review;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.ReviewRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServ {
    private final ReviewRepo reviewRepo;
    private final ItemServ itemServ;

    public ReviewServ(ReviewRepo reviewRepo, ItemServ itemServ) {
        this.reviewRepo = reviewRepo;
        this.itemServ = itemServ;
    }

    public void saveReview(Review review, Long id, User user) {
        Item item = itemServ.findItem(id);
        review.setItem(item);
        review.setUser(user);
        reviewRepo.save(review);
    }

    public Review findById(Long id) {
        Optional<Review> review = reviewRepo.findById(id);
        return review.orElse(null);
    }

    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }
}
