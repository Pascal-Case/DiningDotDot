package jyang.diningdotdot.dto.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {
    private Long storeId;
    private String strReservationDate; // 사용자 입력 날짜 문자열
    private String strReservationTime; // 사용자 입력 시간 문자열

    private LocalDateTime reservationTime;

    @NotNull(message = "예약 인원을 입력해야 합니다.")
    @Min(value = 1, message = "예약 인원은 최소 1명 이상이어야 합니다.")
    private Integer partySize;
    private String memo;

    public void generateReservationTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = LocalDate.parse(this.strReservationDate, dateFormatter);
        LocalTime time = LocalTime.parse(this.strReservationTime, timeFormatter);

        this.reservationTime = LocalDateTime.of(date, time);
    }
}
