package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Review;
import com.brena.ecommerce.repositories.ReviewRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServ {
    private final ReviewRepo reviewRepo;

    public ReviewServ(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public void saveReview(Review review) {
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
