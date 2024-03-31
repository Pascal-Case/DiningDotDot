package jyang.diningdotdot.dto.oauth2;

public interface OAuth2Response {
    String getProvider(); // 제공자 (naver, google..)

    String getProviderId(); // 발급 아이디

    String getEmail(); // 이메일

    String getName();

    String getPhone();

    String getNickName();
}
