package jyang.diningdotdot.store;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jyang.diningdotdot.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity {

    private String name;
}
