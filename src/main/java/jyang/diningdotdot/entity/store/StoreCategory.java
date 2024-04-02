package jyang.diningdotdot.entity.store;

import jakarta.persistence.Entity;
import jyang.diningdotdot.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCategory extends BaseEntity {

    private String name;
}
