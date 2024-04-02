package jyang.diningdotdot.entity.store;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jyang.diningdotdot.dto.store.StoreDTO;
import jyang.diningdotdot.entity.common.Address;
import jyang.diningdotdot.entity.common.BaseEntity;
import jyang.diningdotdot.entity.user.Partner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Store extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private StoreCategory storeCategory;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    private String registrationNumber;

    private Integer capacity;

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalTime lastOrderTime;

    private String phone;

    private Double rating;

    private String description;

    @Embedded
    private Address address;

    public void updateStore(StoreDTO storeDTO, StoreCategory storeCategory) {
        this.name = storeDTO.getName();
        this.storeCategory = storeCategory;
        this.registrationNumber = storeDTO.getRegistrationNumber();
        this.capacity = storeDTO.getCapacity();
        this.openTime = storeDTO.getOpenTime();
        this.closeTime = storeDTO.getCloseTime();
        this.lastOrderTime = storeDTO.getLastOrderTime();
        this.phone = storeDTO.getPhone();
        this.description = storeDTO.getDescription();
        if (storeDTO.toAddress() != null) {
            this.address = storeDTO.toAddress();
        }
    }
}
