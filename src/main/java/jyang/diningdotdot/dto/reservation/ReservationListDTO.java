package jyang.diningdotdot.dto.reservation;

import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationListDTO {
    private Long id;
    private Long userId;
    private String storeName;
    private LocalDateTime reservationTime;
    private Integer partySize;
    private ReservationStatus reservationStatus;

    public static ReservationListDTO fromEntity(Reservation reservation) {
        return ReservationListDTO.builder()
                .id(reservation.getId())
                .userId(reservation.getBaseUser().getId())
                .storeName(reservation.getStore().getName())
                .reservationTime(reservation.getReservationTime())
                .partySize(reservation.getPartySize())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}
