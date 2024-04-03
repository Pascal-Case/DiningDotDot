package jyang.diningdotdot.controller.store;

import jyang.diningdotdot.dto.store.StoreListDTO;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreService storeService;

    @GetMapping
    public String storeListPage(
            Model model,
            @PageableDefault(size = 4) Pageable pageable) {
        Slice<StoreListDTO> storeSlice = storeService.getStoreSlice(pageable);
        model.addAttribute("storeSlice", storeSlice);
        return "stores/list";
    }
}
