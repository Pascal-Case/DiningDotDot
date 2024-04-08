package jyang.diningdotdot.dto.review;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jyang.diningdotdot.entity.review.Review;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewUpdateDTO {
    private Long reviewId;
    private String content;
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;

    public static ReviewUpdateDTO fromEntity(Review review) {
        return ReviewUpdateDTO.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}
