package jyang.diningdotdot.dto.oauth2;

import jyang.diningdotdot.entity.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final Role role;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(role.name()));
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getUsername() {
        return oAuth2Response.getEmail();
    }

    public String getPhone() {
        return oAuth2Response.getPhone();
    }

    public String getNickName() {
        return oAuth2Response.getNickName();
    }
}
