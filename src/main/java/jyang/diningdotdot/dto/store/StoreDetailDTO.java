package jyang.diningdotdot.dto.store;

import jyang.diningdotdot.entity.store.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDetailDTO {
    private StoreListDTO storeListDTO;
    private String city;
    private String street;
    private String zipcode;
    private Double rating;

    public static StoreDetailDTO fromEntity(Store store) {
        return StoreDetailDTO.builder()
                .storeListDTO(StoreListDTO.fromEntity(store))
                .city(store.getAddress().getCity())
                .street(store.getAddress().getStreet())
                .zipcode(store.getAddress().getZipcode())
                .rating(store.getRating())
                .build();
    }
}
