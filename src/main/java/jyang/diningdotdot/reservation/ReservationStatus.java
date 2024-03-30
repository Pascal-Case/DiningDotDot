package jyang.diningdotdot.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("대기"),
    APPROVED("승인"),
    REJECTED("거절"),
    CONFIRMED("확정"),
    CANCELLED("취소"),
    COMPLETED("완료"),
    NOSHOW("노쇼");

    private final String description;

}
