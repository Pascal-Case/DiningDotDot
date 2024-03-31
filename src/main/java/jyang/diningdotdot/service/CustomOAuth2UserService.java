package jyang.diningdotdot.service;

import jyang.diningdotdot.dto.oauth2.CustomOAuth2User;
import jyang.diningdotdot.dto.oauth2.KakaoResponse;
import jyang.diningdotdot.dto.oauth2.NaverResponse;
import jyang.diningdotdot.dto.oauth2.OAuth2Response;
import jyang.diningdotdot.entity.user.AuthType;
import jyang.diningdotdot.entity.user.Role;
import jyang.diningdotdot.entity.user.User;
import jyang.diningdotdot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        System.out.println(registrationId);

        OAuth2Response oAuth2Response = createOAuth2Response(registrationId, oAuth2User.getAttributes());


        User user = userRepository.findByUsername(oAuth2Response.getEmail())
                .map(existingUser ->
                        existingUser.updateWithOAuth2Response(oAuth2Response))
                .orElseGet(() -> registerNewUser(oAuth2Response, registrationId));

        userRepository.save(user);

        return new CustomOAuth2User(oAuth2Response, Role.ROLE_USER);
    }

    private OAuth2Response createOAuth2Response(String registrationId, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return new NaverResponse(attributes);
        } else if ("kakao".equals(registrationId)) {
            return new KakaoResponse(attributes);
        } else {
            throw new OAuth2AuthenticationException(new OAuth2Error("unsupported_provider"), "Unsupported provider");
        }
    }

    private User registerNewUser(OAuth2Response oAuth2Response, String registrationId) {
        // 새로운 사용자 등록 로직
        return User.builder()
                .username(oAuth2Response.getEmail())
                .name(oAuth2Response.getName())
                .nickname(oAuth2Response.getNickName())
                .phone(oAuth2Response.getPhone())
                .role(Role.ROLE_USER)
                .authType(AuthType.OAUTH)
                .providerId(oAuth2Response.getProviderId())
                .provider(registrationId)
                .build();
    }

}
