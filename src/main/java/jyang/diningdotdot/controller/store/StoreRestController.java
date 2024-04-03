package jyang.diningdotdot.controller.store;

import jyang.diningdotdot.dto.store.StoreListDTO;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreRestController {
    private final StoreService storeService;

    @GetMapping("/stores/slice")
    public Slice<StoreListDTO> getStoresSlice(
            @PageableDefault Pageable pageable) {
        return storeService.getStoreSlice(pageable);
    }
}
