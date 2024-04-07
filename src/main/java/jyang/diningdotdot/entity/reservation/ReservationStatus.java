package jyang.diningdotdot.entity.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("대기", "pending"), // 예약 생성 시 대기 상태
    APPROVED("승인", "approve"), // 매장 관리자가 예약을 승인한 상태
    REJECTED("거절", "reject"), // 매장 관리자가 예약을 거절한 상태
    CONFIRMED("확정", "confirm"), // 고객이 매장 방문시 예약을 확정
    CANCELLED("취소", "cancel"), // 고객, 매장 관리자가 예약을 취소
    COMPLETED("완료", "complete"), // 고객이 매장 이용 완료
    NOSHOW("노쇼", "noshow"); // 고객이 노쇼

    private final String description;
    private final String action;

    public static ReservationStatus fromAction(String action) {
        for (ReservationStatus status : values()) {
            if (status.getAction().equalsIgnoreCase(action)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + action + "]");
    }
}
