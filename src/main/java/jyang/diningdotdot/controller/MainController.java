package jyang.diningdotdot.controller;

import jyang.diningdotdot.dto.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails userDetails) {

                String id = userDetails.getUsername();
                String name = userDetails.getName();

                // 권한(역할) 목록을 스트링으로 변환
                String roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "));

                model.addAttribute("id", id);
                model.addAttribute("role", roles);
                model.addAttribute("name", name);
            }
        }

        return "main";
    }
}
