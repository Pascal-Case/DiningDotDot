package jyang.diningdotdot.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jyang.diningdotdot.entity.common.Address;
import jyang.diningdotdot.entity.common.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class BaseUser extends BaseEntity {
    @Column(unique = true)
    @Email
    private String username; // username : email

    private String nickname;

    private String name;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;
}
