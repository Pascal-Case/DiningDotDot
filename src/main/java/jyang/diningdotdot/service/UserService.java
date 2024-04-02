package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.dto.common.JoinForm;
import jyang.diningdotdot.entity.user.AuthType;
import jyang.diningdotdot.entity.user.User;
import jyang.diningdotdot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jyang.diningdotdot.entity.user.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void joinProcess(JoinForm joinForm) {
        if (userRepository.existsByUsername(joinForm.getUsername())) {
            throw new RuntimeException("존재하는 메일 주소");
        }

        User user = User.builder()
                .username(joinForm.getUsername())
                .name(joinForm.getName())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .phone(joinForm.getPhone())
                .role(ROLE_USER)
                .address(joinForm.toAddress())
                .authType(AuthType.LOCAL)
                .build();

        userRepository.save(user);
    }

    private User getPartner(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
