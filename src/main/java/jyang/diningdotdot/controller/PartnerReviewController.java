package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.review.ReviewListDTO;
import jyang.diningdotdot.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/partners/reviews")
@RequiredArgsConstructor
public class PartnerReviewController {
    private final AuthenticationFacade authenticationFacade;
    private final ReviewService reviewService;

    @GetMapping
    public String myStoresReviewsPage(Model model) {
        Long partnerId = authenticationFacade.getCurrentUserId();

        List<ReviewListDTO> reviews = reviewService.getMyStoresReviews(partnerId);
        model.addAttribute("reviews", reviews);
        return "/partners/reviews/list";
    }
}
