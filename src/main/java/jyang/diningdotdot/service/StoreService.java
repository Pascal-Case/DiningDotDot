package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.store.StoreDTO;
import jyang.diningdotdot.dto.store.StoreDetailDTO;
import jyang.diningdotdot.dto.store.StoreListDTO;
import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.store.StoreCategory;
import jyang.diningdotdot.entity.user.Partner;
import jyang.diningdotdot.repository.PartnerRepository;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import jyang.diningdotdot.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
        Store store = getStoreById(storeDTO.getId());
        StoreCategory storeCategory = getStoreCategory(storeDTO.getCategoryId());
        store.updateStore(storeDTO, storeCategory);
    }

    @Transactional
    public void deleteStore(Long id) {
        storeRepository.delete(getStoreById(id));
    }

    public List<StoreListDTO> findStoreListByCurrentPartner() {
        String currentPartnerUsername = authenticationFacade.getCurrentUsername();
        Partner partner = getPartner(currentPartnerUsername);
        return storeRepository.findByPartner(partner)
                .stream()
                .map(StoreListDTO::fromEntity)
                .toList();
    }

    public Slice<StoreListDTO> getStoreSlice(String query, Pageable pageable) {
        Slice<Store> storeSlice = storeRepository.findAll(pageable);
        List<StoreListDTO> storeList = storeSlice.getContent().stream()
                .map(StoreListDTO::fromEntity)
                .toList();
        return new SliceImpl<>(storeList, pageable, storeSlice.hasNext());
    }

    public Slice<StoreListDTO> searchStoresByQuery(String query, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            return storeRepository.findAll(pageable).map(StoreListDTO::fromEntity);
        } else {
            return storeRepository.findByQuery(query, pageable).map(StoreListDTO::fromEntity);
        }
    }

    public StoreDTO getStoreDtoById(Long id) {
        return StoreDTO.fromEntity(getStoreById(id));
    }

    public StoreDetailDTO getStoreDetailDtoById(Long id) {
        return StoreDetailDTO.fromEntity(getStoreById(id));
    }


    public StoreCategory getStoreCategory(Long id) {
        return storeCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StoreCategory not found"));
    }

    public Partner getPartner(String currentPartnerUsername) {
        return partnerRepository.findByUsername(currentPartnerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Partner not found"));
    }

    public Store getStoreById(Long id) {
        return storeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Store not found"));
    }
}
