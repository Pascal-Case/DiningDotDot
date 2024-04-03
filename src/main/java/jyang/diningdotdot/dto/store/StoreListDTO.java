package jyang.diningdotdot.dto.store;

import jyang.diningdotdot.entity.common.Address;
import jyang.diningdotdot.entity.store.Store;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreListDTO {
    private Long id;
    private String name;
    private String categoryName;
    private Integer capacity;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime lastOrderTime;
    private String phone;
    private String description;
    private String city;
    private String street;
    private String zipcode;

    public Address toAddress() {
        return new Address(city, street, zipcode);
    }

    public static StoreListDTO fromEntity(Store store) {
        return StoreListDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .categoryName(store.getStoreCategory().getName())
                .capacity(store.getCapacity())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .lastOrderTime(store.getLastOrderTime())
                .phone(store.getPhone())
                .description(store.getDescription())
                .city(store.getAddress().getCity())
                .street(store.getAddress().getStreet())
                .zipcode(store.getAddress().getZipcode())
                .build();
    }
}
