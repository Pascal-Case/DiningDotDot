package jyang.diningdotdot.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jyang.diningdotdot.entity.store.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Partner extends BaseUser {

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<Store> stores;

}
