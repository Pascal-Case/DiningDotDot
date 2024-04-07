package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDTO;
import jyang.diningdotdot.dto.reservation.ReservationDetailDTO;
import jyang.diningdotdot.dto.reservation.ReservationListDTO;
import jyang.diningdotdot.dto.store.StoreDetailDTO;
import jyang.diningdotdot.service.ReservationService;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my/reservations")
public class MyReservationController {
    private final ReservationService reservationService;
    private final StoreService storeService;
    private final AuthenticationFacade authenticationFacade;

    /**
     * 마이 예약 작성
     */
    @PostMapping
    public String reservationProcess(
            @ModelAttribute ReservationDTO reservationDTO,
            BindingResult result,
            Model model) {
        reservationDTO.generateReservationTime(); // reservationTime 생성
        Long storeId = reservationDTO.getStoreId();
        if (result.hasErrors()) {
            StoreDetailDTO storeDetailDTO = storeService.getStoreDetailDtoById(storeId);
            model.addAttribute("storeDetail", storeDetailDTO);
            model.addAttribute("storeId", storeId);
            return "stores/detail";
        }

        if (reservationDTO.getReservationTime().isBefore(LocalDateTime.now())) {
            StoreDetailDTO storeDetailDTO = storeService.getStoreDetailDtoById(storeId);
            model.addAttribute("storeDetail", storeDetailDTO);
            model.addAttribute("storeId", storeId);
            result.rejectValue("reservationDate", "예약 시간은 미래여야 합니다.");
            return "stores/detail";
        }

        reservationService.reservationProcess(reservationDTO);
        return "redirect:/my/reservations";
    }

    /**
     * 마이 예약 리스트
     */
    @GetMapping
    public String reservationListPage(Model model) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        String currentUserName = authenticationFacade.getCurrentName();
        List<ReservationListDTO> myReservations = reservationService.getMyReservations(currentUserId);

        model.addAttribute("reservationList", myReservations);
        model.addAttribute("myName", currentUserName);
        return "my/reservations/list";
    }

    /**
     * 마이 예약 상세 페이지
     */
    @GetMapping("/detail/{reservationId}")
    public String myReservationDetailPage(Model model, @PathVariable Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        ReservationDetailDTO reservationDetail = reservationService.getMyReservationDetail(reservationId, currentUserId);

        model.addAttribute("reservation", reservationDetail);
        return "my/reservations/detail";
    }

    /**
     * 마이 예약 취소
     */
    @PostMapping("/cancel/{reservationId}")
    public ResponseEntity<?> reservationCancelProcess(@PathVariable Long reservationId) {
        Long currentUserId = authenticationFacade.getCurrentUserId();
        reservationService.cancelProcess(reservationId, currentUserId);
        return ResponseEntity.ok().build();
    }
}
