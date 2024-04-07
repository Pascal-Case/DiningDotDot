package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.review.ReviewDTO;
import jyang.diningdotdot.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my/reviews")
public class MyReviewController {
    private final ReviewService reviewService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public String postReview(@ModelAttribute @Valid ReviewDTO reviewDTO) {
        Long userId = authenticationFacade.getCurrentUserId();
        reviewService.postReview(reviewDTO, userId);
        return "redirect:/my/reservations";
    }

    @GetMapping
    public String reviewListPage(Model model) {
        return "";
    }

    @PostMapping("/{reviewId}")
    public String updateReview(@PathVariable Long reviewId, Model model) {
        return "";
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId) {
        return "";
    }
}
