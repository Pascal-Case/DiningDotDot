package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.dto.review.ReviewDTO;
import jyang.diningdotdot.dto.review.ReviewDetailDTO;
import jyang.diningdotdot.dto.review.ReviewListDTO;
import jyang.diningdotdot.dto.review.ReviewUpdateDTO;
import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.review.Review;
import jyang.diningdotdot.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationService reservationService;

    /**
     * 리뷰 작성
     *
     * @param reviewDTO 리뷰 dto
     * @param userId    유저 id
     */
    @Transactional
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

    /**
     * 내 리뷰 리스트
     *
     * @param userId 유저 id
     */
    public List<ReviewListDTO> getMyReviews(Long userId) {
        return reviewRepository.findAllByUserId(userId)
                .stream().map(ReviewListDTO::fromEntity)
                .toList();
    }

    /**
     * 리뷰 상제 정보
     *
     * @param reviewId 리뷰 id
     */
    public ReviewDetailDTO getReviewDetail(Long reviewId) {
        Review review = getReview(reviewId);

        return ReviewDetailDTO.fromEntity(review);
    }

    /**
     * 리뷰 삭제
     *
     * @param reviewId 리뷰 id
     * @param userId   유저 or 파트너 id
     */

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = getReview(reviewId);

        if (isReviewOwner(review, userId) || isReviewsStoreManager(review, userId)) {
            reviewRepository.delete(review);
        } else {
            throw new AccessDeniedException("You do not have permission to access this review.");
        }

    }

    /**
     * 리뷰 수정을 위한 리뷰 찾기
     *
     * @param reviewId 리뷰 id
     * @param userId   유저 id
     * @return 리뷰
     */
    public ReviewUpdateDTO getMyReview(Long reviewId, Long userId) {
        Review review = getReview(reviewId);

        if (!isReviewOwner(review, userId)) {
            throw new AccessDeniedException("You do not have permission to access this review.");
        }

        return ReviewUpdateDTO.fromEntity(review);
    }

    /**
     * 리뷰 업데이트
     *
     * @param reviewUpdateDTO 리뷰 DTO
     * @param userId          유저 id
     */
    @Transactional
    public void updateReview(ReviewUpdateDTO reviewUpdateDTO, Long userId) {
        Review review = getReview(reviewUpdateDTO.getReviewId());

        if (!isReviewOwner(review, userId)) {
            throw new AccessDeniedException("You do not have permission to access this review.");
        }

        review.updateReview(reviewUpdateDTO);
    }

    /**
     * 내 가게의 리뷰 가져오기
     *
     * @param partnerId 파트너 id
     * @return 리뷰 dto 리스트
     */
    public List<ReviewListDTO> getMyStoresReviews(Long partnerId) {
        return reviewRepository.findAllByPartnerId(partnerId)
                .stream().map(ReviewListDTO::fromEntity)
                .toList();
    }

    // 리뷰 id로 리뷰 가져오기
    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
    }

    // 리뷰 오너인지 체크
    private boolean isReviewOwner(Review review, Long id) {
        return review.getReservation().getBaseUser().getId().equals(id);
    }

    // 리뷰 가게 관리자인지 체크
    private boolean isReviewsStoreManager(Review review, Long id) {
        return review.getReservation().getStore().getPartner().getId().equals(id);
    }


}
