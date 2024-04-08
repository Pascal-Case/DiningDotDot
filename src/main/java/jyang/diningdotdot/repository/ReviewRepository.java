package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r where r.reservation.baseUser.id = :userId ORDER BY r.createdAt DESC")
    List<Review> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT  r FROM Review r where r.reservation.store.partner.id = :partnerId ORDER BY r.createdAt DESC")
    List<Review> findAllByPartnerId(@Param("partnerId") Long partnerId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.id = :reviewId AND r.reservation.store.partner.id = :partnerId")
    boolean existsByReviewIdAndPartnerId(@Param("reviewId") Long reviewId, @Param("partnerId") Long partnerId);

}
