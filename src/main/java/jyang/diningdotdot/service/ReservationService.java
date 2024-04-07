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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jyang.diningdotdot.entity.reservation.ReservationStatus.APPROVED;
import static jyang.diningdotdot.entity.reservation.ReservationStatus.PENDING;

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
                .reservationStatus(PENDING)
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
     * 예약을 확정
     *
     * @param reservationId 예약 id
     * @param currentUserId 파트너 id
     */
    @Transactional
    public void confirmProcess(Long reservationId, Long currentUserId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .filter(res -> res.getStore().getPartner().getId().equals(currentUserId))
                .filter(res -> res.getReservationStatus().equals(APPROVED))
                .orElseThrow(() -> new AccessDeniedException("You do not have permission to view this reservation."));
        reservation.setToConfirmed();
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
     * 관리 매장 초기 진입시 보여줄 대기 예약 리스트
     *
     * @param currentUserId 유저 id
     * @return 예약 리스트
     */
    public List<ReservationListDTO> getMyStoresPendingReservations(Long currentUserId) {
        return reservationRepository.findPendingReservationsByPartnerId(currentUserId, PENDING)
                .stream().map(ReservationListDTO::fromEntity).toList();

    }

    /**
     * 관리 매장 예약 리스트 상태에 따른 필터링
     *
     * @param statusList 상태 리스트
     * @return 예약 리스트
     */
    public List<ReservationListDTO> getReservationsByStatusList(List<ReservationStatus> statusList) {
        return reservationRepository.findByReservationStatusIn(statusList)
                .stream().map(ReservationListDTO::fromEntity).toList();
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
     * 매장 관리 예약 상세 정보
     *
     * @param reservationId 예약 id
     * @param currentUserId 유저 id
     * @return 예약 상세 정보
     */
    public ReservationDetailDTO getMyStoresReservationDetail(Long reservationId, Long currentUserId) {
        return reservationRepository.findById(reservationId)
                .filter(reservation -> reservation.getStore().getPartner().getId().equals(currentUserId))
                .map(ReservationDetailDTO::fromEntity)
                .orElseThrow(() -> new AccessDeniedException("You do not have permission to view this reservation."));

    }

    /**
     * 예약 상태 변경
     *
     * @param reservationId 예약 id
     * @param currentUserId 유저 id
     * @param status        상태
     */
    @Transactional
    public void changeReservationStatus(Long reservationId, Long currentUserId, ReservationStatus status) {
        // 체크


        Reservation reservation = reservationRepository.findById(reservationId)
                .filter(res -> res.getStore().getPartner().getId().equals(currentUserId))
                .orElseThrow(() -> new AccessDeniedException("You do not have permission to view this reservation."));

        switch (status) {
            case PENDING -> reservation.setToPending();
            case NOSHOW -> reservation.setToNoShow();
            case CANCELLED -> reservation.setToCanceled();
            case APPROVED -> reservation.setToApproved();
            case COMPLETED -> reservation.setToCompleted();
            case CONFIRMED -> reservation.setToConfirmed();
            case REJECTED -> reservation.setToRejected();
        }

    }

    /* 유틸 메서드 */

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
