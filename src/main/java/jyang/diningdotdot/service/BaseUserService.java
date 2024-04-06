package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.entity.user.BaseUser;
import jyang.diningdotdot.repository.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseUserService {

    private final BaseUserRepository baseUserRepository;

    public BaseUser findBaseUserById(Long id) {
        return baseUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}