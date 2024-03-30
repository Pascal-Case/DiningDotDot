package jyang.diningdotdot.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jyang.diningdotdot.entity.common.Address;
import jyang.diningdotdot.entity.common.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {
    @Column(unique = true)
    private String username;

    private String nickname;

    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;
}
