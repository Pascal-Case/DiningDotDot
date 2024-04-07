package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.reservation.Reservation;
import jyang.diningdotdot.entity.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBaseUserId(Long baseUser_id);

    @Query("SELECT r FROM Reservation r WHERE r.store.partner.id = :partnerId AND r.reservationStatus = :status ORDER BY r.createdAt DESC")
    List<Reservation> findByPartnerIdAndReservationStatus(@Param("partnerId") Long partnerId, @Param("status") ReservationStatus status);

    @Query("SELECT r FROM Reservation r WHERE r.store.partner.id = :partnerId AND r.reservationStatus = :status ORDER BY r.createdAt DESC")
    List<Reservation> findPendingReservationsByPartnerId(@Param("partnerId") Long partnerId, @Param("status") ReservationStatus status);

    List<Reservation> findByReservationStatusIn(Collection<ReservationStatus> statuses);

    boolean existsByIdAndBaseUserId(Long reservationId, Long userId);
}
