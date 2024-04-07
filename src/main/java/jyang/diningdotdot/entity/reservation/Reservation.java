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

import static jyang.diningdotdot.entity.reservation.ReservationStatus.*;

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

    // 예약 상태를 대기로 변경
    public void setToPending() {
        this.reservationStatus = PENDING;
    }

    // 예약 상태를 취소로 변경
    public void setToCanceled() {
        this.reservationStatus = CANCELLED;
    }

    // 예약 상태를 승인으로 변경
    public void setToApproved() {
        this.reservationStatus = APPROVED;
    }

    // 예약 상태를 거절로 변경
    public void setToRejected() {
        this.reservationStatus = REJECTED;
    }

    // 예약 상태를 확정으로 변경
    public void setToConfirmed() {
        this.reservationStatus = CONFIRMED;
    }

    // 예약 상태를 완료로 변경
    public void setToCompleted() {
        this.reservationStatus = COMPLETED;
    }

    // 예약 상태를 노쇼로 변경
    public void setToNoShow() {
        this.reservationStatus = NOSHOW;
    }
}
