package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDetailDTO;
import jyang.diningdotdot.dto.reservation.ReservationListDTO;
import jyang.diningdotdot.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyController {
    private final ReservationService reservationService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/reservations")
    public String reservationListPage(Model model) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        String currentUserName = authenticationFacade.getCurrentName();
        List<ReservationListDTO> myReservations = reservationService.getMyReservations(currentUserId);

        model.addAttribute("reservationList", myReservations);
        model.addAttribute("myName", currentUserName);
        return "my/reservations/list";
    }

    @GetMapping("/reservations/detail/{reservationId}")
    public String myReservationDetailPage(Model model, @PathVariable Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        ReservationDetailDTO reservationDetail = reservationService.getMyReservationDetail(reservationId, currentUserId);

        model.addAttribute("reservation", reservationDetail);
        return "my/reservations/detail";
    }

    @PostMapping("/reservations/cancel/{reservationId}")
    public ResponseEntity<?> reservationCancelProcess(@PathVariable Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        reservationService.cancelProcess(reservationId, currentUserId);
        return ResponseEntity.ok().build();
    }
}
