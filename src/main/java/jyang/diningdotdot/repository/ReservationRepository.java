package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBaseUserId(Long baseUser_id);

    boolean existsByIdAndBaseUserId(Long reservationId, Long userId);
}
