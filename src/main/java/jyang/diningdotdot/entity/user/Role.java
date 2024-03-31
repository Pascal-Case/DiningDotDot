package jyang.diningdotdot.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("일반 사용자 권한"),
    ROLE_MANAGER("매니저 권한"),
    ROLE_ADMIN("관리자 권한");

    private final String description;
}
