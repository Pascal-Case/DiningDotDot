package jyang.diningdotdot.config;

import jyang.diningdotdot.dto.CustomUserDetails;
import jyang.diningdotdot.dto.oauth2.CustomOAuth2User;
import jyang.diningdotdot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UserService userService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Object getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getPrincipal();
        }
        return null;
    }

    public String getCurrentUsername() {
        Object principal = getCurrentUser();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUsername();
        } else if (principal instanceof CustomOAuth2User customOAuth2User) {
            return customOAuth2User.getUsername();
        }
        return null;
    }

    public Long getCurrentUserId() {
        Object principal = getCurrentUser();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUserId();
        } else if (principal instanceof CustomOAuth2User customOAuth2User) {
            return userService.fineUserByUsername(customOAuth2User.getUsername()).getId();
        }
        return null;
    }

    public String getCurrentName() {
        Object principal = getCurrentUser();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getName();
        } else if (principal instanceof CustomOAuth2User customOAuth2User) {
            return customOAuth2User.getName();
        }
        return null;
    }

}
