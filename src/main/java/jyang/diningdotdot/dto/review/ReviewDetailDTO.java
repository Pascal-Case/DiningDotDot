package jyang.diningdotdot.dto.review;

import jyang.diningdotdot.entity.review.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewDetailDTO {
    private Long reviewId;
    private Long storeId;
    private String storeName;
    private String reviewerName;
    private String reviewerUsername;
    private String partnerUsername;
    private Double rating;
    private String content;
    private LocalDateTime visitDate;
    private LocalDateTime postDate;

    public static ReviewDetailDTO fromEntity(Review review) {
        return ReviewDetailDTO.builder()
                .reviewId(review.getId())
                .storeId(review.getReservation().getStore().getId())
                .storeName(review.getReservation().getStore().getName())
                .reviewerUsername(review.getReservation().getBaseUser().getUsername())
                .reviewerName(review.getReservation().getBaseUser().getName())
                .rating(review.getRating())
                .content(review.getContent())
                .visitDate(review.getReservation().getReservationTime())
                .postDate(review.getCreatedAt())
                .build();
    }
}
