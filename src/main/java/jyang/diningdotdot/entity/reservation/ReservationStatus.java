package jyang.diningdotdot.entity.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("대기"), // 예약 생성 시 대기 상태
    APPROVED("승인"), // 매장 관리자가 예약을 승인한 상태
    REJECTED("거절"), // 매장 관리자가 예약을 거절한 상태
    CONFIRMED("확정"), // 고객이 매장 방문시 예약을 확정
    CANCELLED("취소"), // 고객, 매장 관리자가 예약을 취소
    COMPLETED("완료"), // 고객이 매장 이용 완료
    NOSHOW("노쇼"); // 고객이 노쇼

    private final String description;

}
