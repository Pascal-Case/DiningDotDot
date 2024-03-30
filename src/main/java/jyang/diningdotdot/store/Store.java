package jyang.diningdotdot.store;

import jakarta.persistence.*;
import jyang.diningdotdot.common.Address;
import jyang.diningdotdot.common.BaseEntity;
import jyang.diningdotdot.user.Partner;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Store extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    private Integer capacity;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private LocalDateTime lastOrderTime;

    private String phone;

    private String description;

    @Embedded
    private Address address;
}
