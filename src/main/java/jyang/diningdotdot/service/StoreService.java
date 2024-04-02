package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.store.StoreDTO;
import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.store.StoreCategory;
import jyang.diningdotdot.entity.user.Partner;
import jyang.diningdotdot.repository.PartnerRepository;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import jyang.diningdotdot.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final PartnerRepository partnerRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final AuthenticationFacade authenticationFacade;


    @Transactional
    public void registerStore(StoreDTO storeDTO) {
        String currentPartnerUsername = authenticationFacade.getCurrentUsername();
        Partner partner = getPartner(currentPartnerUsername);
        StoreCategory storeCategory = getStoreCategory(storeDTO.getCategoryId());
        Store store = Store.builder()
                .name(storeDTO.getName())
                .storeCategory(storeCategory)
                .partner(partner)
                .registrationNumber(storeDTO.getRegistrationNumber())
                .capacity((storeDTO.getCapacity()))
                .openTime(storeDTO.getOpenTime())
                .closeTime(storeDTO.getCloseTime())
                .lastOrderTime(storeDTO.getLastOrderTime())
                .phone(storeDTO.getPhone())
                .description(storeDTO.getDescription())
                .address(storeDTO.toAddress())
                .build();

        storeRepository.save(store);
    }

    @Transactional
    public void updateStore(StoreDTO storeDTO) {
        Store store = getStore(storeDTO.getId());
        StoreCategory storeCategory = getStoreCategory(storeDTO.getCategoryId());
        store.updateStore(storeDTO, storeCategory);
    }

    @Transactional
    public void deleteStore(Long id) {
        storeRepository.delete(getStore(id));
    }

    public List<Store> findStoreListByCurrentPartner() {
        String currentPartnerUsername = authenticationFacade.getCurrentUsername();
        Partner partner = getPartner(currentPartnerUsername);
        System.out.println("partner = " + partner);
        return storeRepository.findByPartner(partner);
    }


    public StoreDTO getStoreDtoById(Long id) {
        Store store = getStore(id);
        return StoreDTO.fromEntity(store);
    }


    private StoreCategory getStoreCategory(Long id) {
        return storeCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StoreCategory not found"));
    }

    private Partner getPartner(String currentPartnerUsername) {
        return partnerRepository.findByUsername(currentPartnerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Partner not found"));
    }

    private Store getStore(Long id) {
        return storeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Store not found"));
    }
}
