package jyang.diningdotdot.entity.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
@Builder
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

}
