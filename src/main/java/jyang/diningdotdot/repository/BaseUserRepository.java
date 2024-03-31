package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.user.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {

    boolean existsByUsername(String username);

    Optional<BaseUser> findByUsername(String username);
}
