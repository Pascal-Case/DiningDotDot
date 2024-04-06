package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.dto.reservation.ReservationDTO;
import jyang.diningdotdot.dto.store.StoreDetailDTO;
import jyang.diningdotdot.service.ReservationService;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final StoreService storeService;
    private final ReservationService reservationService;

    @PostMapping
    public String reservationProcess(
            @ModelAttribute @Valid ReservationDTO reservationDTO,
            BindingResult result,
            Model model,
            @RequestParam("storeId") Long storeId) {
        reservationDTO.generateReservationTime(); // reservationTime 생성

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
        return "redirect:/";
    }
}
