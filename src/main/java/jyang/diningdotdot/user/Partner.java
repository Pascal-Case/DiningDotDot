package jyang.diningdotdot.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jyang.diningdotdot.store.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Partner extends User {
    
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<Store> stores;

}
