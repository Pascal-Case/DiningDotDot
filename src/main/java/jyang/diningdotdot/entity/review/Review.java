package jyang.diningdotdot.entity.review;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jyang.diningdotdot.dto.review.ReviewUpdateDTO;
import jyang.diningdotdot.entity.common.BaseEntity;
import jyang.diningdotdot.entity.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String content;
    private Double rating;


    public void updateReview(ReviewUpdateDTO reviewUpdateDTO) {
        this.content = reviewUpdateDTO.getContent();
        this.rating = reviewUpdateDTO.getRating();
    }
}
