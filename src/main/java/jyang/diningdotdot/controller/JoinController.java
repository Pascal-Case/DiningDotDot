package jyang.diningdotdot.controller;

import jyang.diningdotdot.service.PartnerService;
import jyang.diningdotdot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    private final UserService userService;
    private final PartnerService partnerService;
}
