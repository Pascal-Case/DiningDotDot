package jyang.diningdotdot.review;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jyang.diningdotdot.common.BaseEntity;
import jyang.diningdotdot.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Review extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String content;
    private Integer rating;

}
