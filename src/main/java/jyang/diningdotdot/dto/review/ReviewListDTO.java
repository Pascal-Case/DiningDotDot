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
public class ReviewListDTO {
    private Long reviewId;
    private Long reservationId;
    private String storeName;
    private Double rating;
    private LocalDateTime visitDate;
    private LocalDateTime postDate;

    public static ReviewListDTO fromEntity(Review review) {
        return ReviewListDTO.builder()
                .reviewId(review.getId())
                .reservationId(review.getReservation().getId())
                .storeName(review.getReservation().getStore().getName())
                .rating(review.getRating())
                .visitDate(review.getReservation().getReservationTime())
                .postDate(review.getCreatedAt())
                .build();
    }
}
