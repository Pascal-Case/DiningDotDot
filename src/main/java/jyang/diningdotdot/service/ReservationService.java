package jyang.diningdotdot.service;

import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDTO;
import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.user.BaseUser;
import jyang.diningdotdot.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BaseUserService baseUserService;
    private final StoreService storeService;
    private final AuthenticationFacade authenticationFacade;

    public void reservationProcess(ReservationDTO reservationDTO) {
        BaseUser currentUser = baseUserService.findBaseUserById(
                authenticationFacade.getCurrentUserId());
        Store reservationStore = storeService.getStoreById(
                reservationDTO.getStoreId());

        Reservation reservation = Reservation.builder()
                .baseUser(currentUser)
                .store(reservationStore)
                .reservationTime(reservationDTO.getReservationTime())
                .partySize(reservationDTO.getPartySize())
                .reservationStatus(ReservationStatus.PENDING)
                .memo(reservationDTO.getMemo())
                .build();
        reservationRepository.save(reservation);
    }
}
