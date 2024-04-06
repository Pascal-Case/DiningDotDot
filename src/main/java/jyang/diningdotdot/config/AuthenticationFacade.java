package jyang.diningdotdot.config;

import jyang.diningdotdot.dto.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private CustomUserDetails getCustomUserDetails() {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return (CustomUserDetails) principal;
            }
        }
        return null;
    }

    public String getCurrentUsername() {
        CustomUserDetails userDetails = getCustomUserDetails();
        if (userDetails != null) {
            return userDetails.getUsername();
        }
        return null;
    }

    public Long getCurrentUserId() {
        CustomUserDetails userDetails = getCustomUserDetails();
        if (userDetails != null) {
            return userDetails.getUserId();
        }
        return null;
    }

    public String getCurrentName() {
        CustomUserDetails userDetails = getCustomUserDetails();
        if (userDetails != null) {
            return userDetails.getName();
        }
        return null;
    }

}
