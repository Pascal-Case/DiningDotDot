package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDTO;
import jyang.diningdotdot.dto.review.ReviewDetailDTO;
import jyang.diningdotdot.dto.review.ReviewListDTO;
import jyang.diningdotdot.dto.store.StoreDetailDTO;
import jyang.diningdotdot.dto.store.StoreListDTO;
import jyang.diningdotdot.repository.ReviewRepository;
import jyang.diningdotdot.service.ReviewService;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;

    // 매장 리스트 페이지
    @GetMapping
    public String storeListPage(
            Model model,
            @PageableDefault(size = 4) Pageable pageable,
            @RequestParam(required = false) String query) {
        Slice<StoreListDTO> storeSlice = storeService.searchStoresByQuery(query, pageable);
        model.addAttribute("storeSlice", storeSlice);
        return "stores/list";
    }

    // 매장 상세 정보 페이지
    @GetMapping("/{storeId}")
    public String storeDetailPage(
            Model model,
            @PathVariable Long storeId
    ) {
        StoreDetailDTO storeDetailDTO = storeService.getStoreDetailDtoById(storeId);
        model.addAttribute("storeDetail", storeDetailDTO);
        model.addAttribute("reservationDTO", new ReservationDTO());
        model.addAttribute("storeId", storeId);
        return "stores/detail";
    }

    // 매장 슬라이스
    @GetMapping("/slice")
    @ResponseBody
    public Slice<StoreListDTO> getStoresSlice(
            @PageableDefault(size = 4) Pageable pageable,
            @RequestParam(required = false) String query) {
        return storeService.searchStoresByQuery(query, pageable);
    }

    // 리뷰 상세 페이지
    @GetMapping("/reviews/{reviewId}")
    public String reviewDetailPage(Model model, @PathVariable Long reviewId) {
        ReviewDetailDTO reviewDetail = reviewService.getReviewDetail(reviewId);

        boolean isReviewOwner = reviewDetail.getReviewerUsername()
                .equals(authenticationFacade.getCurrentUsername());
        boolean isStoreManager = reviewRepository.
                existsByReviewIdAndPartnerId(reviewId, authenticationFacade.getCurrentUserId());

        model.addAttribute("isReviewOwner", isReviewOwner);
        model.addAttribute("isStoreManager", isStoreManager);
        model.addAttribute("reviewDetail", reviewDetail);
        return "/stores/review";
    }

    @GetMapping("{storeId}/reviews")
    public String storeReviewListPage(Model model, @PathVariable Long storeId) {
        List<ReviewListDTO> reviews = reviewService.getStoreReviews(storeId);
        model.addAttribute("reviews", reviews);
        return "stores/reviewList";
    }
}
