package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.dto.common.JoinForm;
import jyang.diningdotdot.entity.user.Partner;
import jyang.diningdotdot.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jyang.diningdotdot.entity.user.Role.ROLE_MANAGER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 파트너 가입
     *
     * @param joinForm 파트너 가입 폼
     */
    @Transactional
    public void joinProcess(JoinForm joinForm) {
        if (partnerRepository.existsByUsername(joinForm.getUsername())) {
            throw new RuntimeException("존재하는 메일 주소");
        }

        Partner partner = Partner.builder()
                .username(joinForm.getUsername())
                .name(joinForm.getName())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .phone(joinForm.getPhone())
                .role(ROLE_MANAGER)
                .address(joinForm.toAddress())
                .build();

        partnerRepository.save(partner);
    }

    /**
     * 파트너 가져오기
     *
     * @param currentPartnerUsername 유저네임
     * @return 파트너
     */
    public Partner getPartner(String currentPartnerUsername) {
        return partnerRepository.findByUsername(currentPartnerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Partner not found"));
    }
}
