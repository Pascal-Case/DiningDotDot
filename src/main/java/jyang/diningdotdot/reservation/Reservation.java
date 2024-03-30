package jyang.diningdotdot.reservation;

import jakarta.persistence.*;
import jyang.diningdotdot.common.BaseEntity;
import jyang.diningdotdot.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Reservation extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;

    private LocalDateTime reservationTime;

    private Integer partySize;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private String memo;


}
