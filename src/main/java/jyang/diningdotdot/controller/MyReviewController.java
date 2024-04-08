package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.review.ReviewDTO;
import jyang.diningdotdot.dto.review.ReviewListDTO;
import jyang.diningdotdot.dto.review.ReviewUpdateDTO;
import jyang.diningdotdot.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my/reviews")
public class MyReviewController {
    private final ReviewService reviewService;
    private final AuthenticationFacade authenticationFacade;

    // 리뷰 작성
    @PostMapping
    public String postReview(@ModelAttribute @Valid ReviewDTO reviewDTO) {
        Long userId = authenticationFacade.getCurrentUserId();
        reviewService.postReview(reviewDTO, userId);
        return "redirect:/my/reviews";
    }

    // 내 리뷰 목록 페이지
    @GetMapping
    public String reviewListPage(Model model) {
        Long userId = authenticationFacade.getCurrentUserId();
        List<ReviewListDTO> reviews = reviewService.getMyReviews(userId);
        model.addAttribute("reviews", reviews);
        return "/my/reviews/list";
    }

    // 리뷰 수정 페이지
    @GetMapping("/edit/{reviewId}")
    public String reviewEditPage(Model model, @PathVariable Long reviewId) {
        Long userId = authenticationFacade.getCurrentUserId();
        ReviewUpdateDTO reviewUpdateDTO = reviewService.getMyReview(reviewId, userId);
        model.addAttribute("reviewUpdateDTO", reviewUpdateDTO);
        return "/my/reviews/edit";
    }

    // 리뷰 수정
    @PostMapping("/update")
    public String updateReview(@ModelAttribute ReviewUpdateDTO reviewUpdateDTO) {
        Long userId = authenticationFacade.getCurrentUserId();
        reviewService.updateReview(reviewUpdateDTO, userId);
        return "redirect:/my/reviews";
    }


}
