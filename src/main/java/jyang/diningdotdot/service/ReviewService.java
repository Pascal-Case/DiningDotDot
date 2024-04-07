package jyang.diningdotdot.service;

import jyang.diningdotdot.dto.review.ReviewDTO;
import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.review.Review;
import jyang.diningdotdot.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationService reservationService;

    /**
     * 리뷰 작성
     *
     * @param reviewDTO 리뷰 dto
     * @param userId    유저 id
     */
    public void postReview(ReviewDTO reviewDTO, Long userId) {
        Long reservationId = reviewDTO.getReservationId();
        // 유효한 예약인지 확인
        reservationService.checkIsReservationOwner(reservationId, userId);

        Reservation reservation = reservationService.getReservationById(reviewDTO.getReservationId());
        
        Review review = Review.builder()
                .reservation(reservation)
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .build();
        reviewRepository.save(review);
    }
}
