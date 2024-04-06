package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partners/reservations")
public class PartnerReservationController {

    private final ReservationService reservationService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public String myStoreReservationListPage(Model model) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
//        List<ReservationListDTO> myStoreReservations = reservationService.getMyStoreReservations(currentUserId)
        return "partners/reservations/list";
    }

}
