package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.reservation.ReservationDTO;
import jyang.diningdotdot.dto.reservation.ReservationDetailDTO;
import jyang.diningdotdot.dto.reservation.ReservationListDTO;
import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.user.BaseUser;
import jyang.diningdotdot.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BaseUserService baseUserService;
    private final StoreService storeService;
    private final AuthenticationFacade authenticationFacade;

    /**
     * 예약 생성
     *
     * @param reservationDTO 예약 정보
     */
    @Transactional
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

    /**
     * 예약을 취소
     *
     * @param reservationId 예약 id
     * @param currentUserId 유저 id
     */
    @Transactional
    public void cancelProcess(Long reservationId, Long currentUserId) {
        checkIsReservationOwner(reservationId, currentUserId);

        Reservation reservation = getReservationById(reservationId);
        reservation.setToCanceled();
    }

    /**
     * 예약 리스트 확인
     *
     * @param currentUserId 유저 id
     * @return 예약 정보 리스트
     */
    public List<ReservationListDTO> getMyReservations(Long currentUserId) {
        return reservationRepository.findByBaseUserId(currentUserId)
                .stream()
                .map(ReservationListDTO::fromEntity)
                .toList();
    }

    /**
     * 예약 상세 정보 확인
     *
     * @param reservationId 예약 id
     * @param currentUserId 유저 id
     * @return 예약 상세 정보
     */
    public ReservationDetailDTO getMyReservationDetail(Long reservationId, Long currentUserId) {
        checkIsReservationOwner(reservationId, currentUserId);
        return ReservationDetailDTO.fromEntity(getReservationById(reservationId));

    }
    
    /**
     * id로 예약 가져오기
     *
     * @param reservationId 예약 id
     * @return 예약 entity
     */
    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    /**
     * 예약의 당사자인지 확인
     *
     * @param reservationId 예약 id
     * @param currentUserId 유저 id
     */
    public void checkIsReservationOwner(Long reservationId, Long currentUserId) {
        boolean isOwner = reservationRepository.existsByIdAndBaseUserId(reservationId, currentUserId);
        if (!isOwner) {
            throw new RuntimeException("Access denied");
        }
    }


}
