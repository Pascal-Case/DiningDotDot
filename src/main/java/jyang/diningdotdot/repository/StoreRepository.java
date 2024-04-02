package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.user.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByPartner(Partner partner);
}
