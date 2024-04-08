package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationFacade authenticationFacade;

    // 로그인 페이지, 이미 로그인했으면 메인 페이지 리디렉션
    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/";
        }

        return "login";
    }
}
