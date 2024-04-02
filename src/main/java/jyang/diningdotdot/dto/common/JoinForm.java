package jyang.diningdotdot.dto.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jyang.diningdotdot.entity.common.Address;
import jyang.diningdotdot.entity.user.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinForm {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "유효하지 않은 이메일 입니다.")
    private String username;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해 주세요.")
    private String password;

    @NotBlank(message = "휴대전화 번호를 입력해 주세요.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "유효하지 않은 휴대전화 번호 입니다.")
    private String phone;

    private String city;
    private String street;
    private String zipcode;

    private Role role;

    public Address toAddress() {
        return new Address(city, street, zipcode);
    }
}
