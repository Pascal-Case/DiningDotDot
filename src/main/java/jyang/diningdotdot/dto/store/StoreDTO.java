package jyang.diningdotdot.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class StoreDTO {
    private Long id;
    @NotBlank(message = "매장명을 입력해 주세요.")
    private String name;
    private Long categoryId;  // 카테고리 ID
    private String registrationNumber;
    private Integer capacity;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime lastOrderTime;
    @NotBlank(message = "휴대전화 번호를 입력해 주세요.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "유효하지 않은 휴대전화 번호 입니다.")
    private String phone;
    private String description;
    private String city;
    private String street;
    private String zipcode;

    public Address toAddress() {
        return new Address(city, street, zipcode);
    }

    public static StoreDTO fromEntity(Store store) {
        return StoreDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .categoryId(store.getStoreCategory().getId())
                .registrationNumber(store.getRegistrationNumber())
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
