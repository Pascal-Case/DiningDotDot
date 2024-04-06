package jyang.diningdotdot.entity.reservation;

import jakarta.persistence.*;
import jyang.diningdotdot.entity.common.BaseEntity;
import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.user.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDateTime reservationTime;

    private Integer partySize;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private String memo;


}
