package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDetailDTO;
import jyang.diningdotdot.dto.reservation.ReservationListDTO;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import jyang.diningdotdot.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partners/reservations")
public class PartnerReservationController {

    private final ReservationService reservationService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public String myStoreReservationListPage(Model model) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        List<ReservationListDTO> myStoresPendingReservations = reservationService.getMyStoresPendingReservations(currentUserId);
        model.addAttribute("reservationList", myStoresPendingReservations);
        return "partners/reservations/list";
    }

    @PostMapping("/filter")
    public String filterReservationsByStatus(@RequestParam List<String> status, Model model) {
        List<ReservationStatus> statusList = status.stream()
                .map(ReservationStatus::valueOf)
                .collect(Collectors.toList());
        List<ReservationListDTO> filteredReservations = reservationService.getReservationsByStatusList(statusList);
        model.addAttribute("reservationList", filteredReservations);
        return "partners/reservations/list";
    }

    @GetMapping("/detail/{reservationId}")
    public String reservationDetailPage(Model model, @PathVariable Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        ReservationDetailDTO reservationDetail = reservationService.getMyStoresReservationDetail(reservationId, currentUserId);
        model.addAttribute("reservation", reservationDetail);
        return "partners/reservations/detail";
    }

    @PostMapping("/{action}/{reservationId}")
    public String handleReservationAction(@PathVariable("action") String action,
                                          @PathVariable("reservationId") Long reservationId,
                                          RedirectAttributes redirectAttributes) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        ReservationStatus status = ReservationStatus.fromAction(action);
        reservationService.changeReservationStatus(reservationId, currentUserId, status);
//        try {
//
//
//            redirectAttributes.addFlashAttribute("successMessage", "예약 상태가 성공적으로 변경되었습니다.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "예약 상태 변경에 실패했습니다.");
//        }
        return "redirect:/partners/reservations";
    }

    @GetMapping("/confirm")
    public String reservationConfirmPage() {
        return "partners/reservations/confirm";
    }

    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId
    ) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        reservationService.confirmProcess(reservationId, currentUserId);
        return ResponseEntity.ok().build();
    }

}
