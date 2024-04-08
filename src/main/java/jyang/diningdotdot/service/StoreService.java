package jyang.diningdotdot.service;

import jakarta.persistence.EntityNotFoundException;
import jyang.diningdotdot.config.AuthenticationFacade;
import jyang.diningdotdot.dto.store.StoreDTO;
import jyang.diningdotdot.dto.store.StoreDetailDTO;
import jyang.diningdotdot.dto.store.StoreListDTO;
import jyang.diningdotdot.entity.store.Store;
import jyang.diningdotdot.entity.store.StoreCategory;
import jyang.diningdotdot.entity.user.Partner;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import jyang.diningdotdot.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PartnerService partnerService;

    /**
     * 매장 등록
     *
     * @param storeDTO 매장 dto
     */
    @Transactional
    public void registerStore(StoreDTO storeDTO) {
        String currentPartnerUsername = authenticationFacade.getCurrentUsername();
        Partner partner = partnerService.getPartner(currentPartnerUsername);
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

    /**
     * 매장 정보 업데이트
     *
     * @param storeDTO 매장 dto
     */
    @Transactional
    public void updateStore(StoreDTO storeDTO) {
        Store store = getStoreById(storeDTO.getId());
        StoreCategory storeCategory = getStoreCategory(storeDTO.getCategoryId());
        store.updateStore(storeDTO, storeCategory);
    }

    /**
     * 매장 삭제
     *
     * @param id 매장 id
     */
    @Transactional
    public void deleteStore(Long id) {
        storeRepository.delete(getStoreById(id));
    }

    /**
     * 관리 매장 리스트
     *
     * @return 매장 리스트
     */
    public List<StoreListDTO> findStoreListByCurrentPartner() {
        String currentPartnerUsername = authenticationFacade.getCurrentUsername();
        Partner partner = partnerService.getPartner(currentPartnerUsername);
        return storeRepository.findByPartner(partner)
                .stream()
                .map(StoreListDTO::fromEntity)
                .toList();
    }

    /**
     * 매장 쿼리 검색
     *
     * @param query    쿼리
     * @param pageable 페이징 객체
     * @return 매장 슬라이스 객체 리스트
     */
    public Slice<StoreListDTO> searchStoresByQuery(String query, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            return storeRepository.findAll(pageable).map(StoreListDTO::fromEntity);
        } else {
            return storeRepository.findByQuery(query, pageable).map(StoreListDTO::fromEntity);
        }
    }

    // 매장 가져오기 StoreDTO 반환
    public StoreDTO getStoreDtoById(Long id) {
        return StoreDTO.fromEntity(getStoreById(id));
    }

    // 매장 가져오기 StoreDetailDTO 반환
    public StoreDetailDTO getStoreDetailDtoById(Long id) {
        return StoreDetailDTO.fromEntity(getStoreById(id));
    }

    // 카테고리 가져오기
    public StoreCategory getStoreCategory(Long id) {
        return storeCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StoreCategory not found"));
    }

    // 매장 가져오기
    public Store getStoreById(Long id) {
        return storeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Store not found"));
    }
}
