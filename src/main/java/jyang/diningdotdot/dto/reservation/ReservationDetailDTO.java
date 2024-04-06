package jyang.diningdotdot.dto.reservation;

import jyang.diningdotdot.entity.common.Address;
import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDetailDTO {
    private Long id;
    private String storeName;
    private LocalDateTime reservationTime;
    private Integer partySize;
    private ReservationStatus reservationStatus;
    private String memo;
    private String storePhone;
    private Address storeAddress;

    public static ReservationDetailDTO fromEntity(Reservation reservation) {
        return ReservationDetailDTO.builder()
                .id(reservation.getId())
                .storeName(reservation.getStore().getName())
                .reservationTime(reservation.getReservationTime())
                .partySize(reservation.getPartySize())
                .reservationStatus(reservation.getReservationStatus())
                .memo(reservation.getMemo())
                .storePhone(reservation.getStore().getPhone())
                .storeAddress(reservation.getStore().getAddress())
                .build();
    }
}
