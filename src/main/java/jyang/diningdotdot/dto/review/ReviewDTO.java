package jyang.diningdotdot.dto.review;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long reservationId;
    private String content;
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;
}
