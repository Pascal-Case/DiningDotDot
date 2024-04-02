package jyang.diningdotdot.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jyang.diningdotdot.dto.oauth2.OAuth2Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseUser {
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    private String provider;
    private String providerId;

    public User updateWithOAuth2Response(OAuth2Response oAuth2Response) {
        this.setNickname(oAuth2Response.getNickName());
        this.setPhone(oAuth2Response.getPhone());
        this.authType = AuthType.OAUTH;
        this.provider = oAuth2Response.getProvider();
        this.providerId = oAuth2Response.getProviderId();
        return this;
    }
}
