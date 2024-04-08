package jyang.diningdotdot.repository;

import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.user.Partner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByPartner(Partner partner);

    @Query("SELECT s FROM Store s " +
            " WHERE " +
            "s.name LIKE %:query% OR " +
            "s.storeCategory.name LIKE %:query% OR " +
            "s.address.city LIKE %:query% OR " +
            "s.address.street LIKE %:query% " +
            "ORDER BY s.rating DESC ")
    Slice<Store> findByQuery(@Param("query") String query, Pageable pageable);
}
