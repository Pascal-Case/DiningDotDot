package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
