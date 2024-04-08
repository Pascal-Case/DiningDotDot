package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDetailDTO;
import jyang.diningdotdot.dto.reservation.ReservationListDTO;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import jyang.diningdotdot.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partners/reservations")
public class PartnerReservationController {

    private final ReservationService reservationService;
    private final AuthenticationFacade authenticationFacade;

    // 매장 예약 리스트 페이지
    @GetMapping
    public String myStoreReservationListPage(Model model) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        List<ReservationListDTO> myStoresPendingReservations = reservationService.getMyStoresPendingReservations(currentUserId);
        model.addAttribute("reservationList", myStoresPendingReservations);
        return "partners/reservations/list";
    }

    // 예약 상태로 필터링
    @PostMapping("/filter")
    public String filterReservationsByStatus(@RequestParam List<String> status, Model model) {
        List<ReservationStatus> statusList = status.stream()
                .map(ReservationStatus::valueOf)
                .collect(Collectors.toList());
        List<ReservationListDTO> filteredReservations = reservationService.getReservationsByStatusList(statusList);
        model.addAttribute("reservationList", filteredReservations);
        return "partners/reservations/list";
    }

    // 예약 상세 페이지
    @GetMapping("/detail/{reservationId}")
    public String reservationDetailPage(Model model, @PathVariable Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        ReservationDetailDTO reservationDetail = reservationService.getMyStoresReservationDetail(reservationId, currentUserId);
        model.addAttribute("reservation", reservationDetail);
        return "partners/reservations/detail";
    }

    // 예약 상태 변경
    @PostMapping("/{action}/{reservationId}")
    public String handleReservationAction(@PathVariable("action") String action,
                                          @PathVariable("reservationId") Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        ReservationStatus status = ReservationStatus.fromAction(action);
        reservationService.changeReservationStatus(reservationId, currentUserId, status);
        return "redirect:/partners/reservations";
    }

    // 예약 확정 페이지
    @GetMapping("/confirm")
    public String reservationConfirmPage() {
        return "partners/reservations/confirm";
    }

    // 예약 확정 처리
    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId
    ) {
        try {
            Long currentUserId = authenticationFacade.getCurrentUserId();
            reservationService.confirmProcess(reservationId, currentUserId);
            return ResponseEntity.ok(Map.of("message", "예약을 성공적으로 확정하였습니다."));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
