package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.user.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    boolean existsByUsername(String username);

    Optional<Partner> findByUsername(String username);
}
