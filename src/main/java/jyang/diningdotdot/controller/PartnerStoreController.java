package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.dto.store.StoreDTO;
import jyang.diningdotdot.dto.store.StoreListDTO;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partners/stores")
public class PartnerStoreController {
    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreService storeService;

    // 내 매장 리스트 페이지
    @GetMapping
    public String myStoreListPage(Model model) {
        List<StoreListDTO> storeList = storeService.findStoreListByCurrentPartner();
        model.addAttribute("storeList", storeList);
        model.addAttribute("storeDTO", new StoreDTO());
        model.addAttribute("categories", storeCategoryRepository.findAll());
        return "partners/stores/list";
    }

    // 매장 등록 처리
    @PostMapping("/register")
    public String registerProcess(
            @ModelAttribute @Valid StoreDTO storeDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "partners/stores/list";
        }
        storeService.registerStore(storeDTO);
        return "redirect:/partners/stores";
    }

    // 매장 정보 수정 페이지
    @GetMapping("/edit/{id}")
    public String getStoreDetails(
            @PathVariable Long id,
            Model model) {
        StoreDTO store = storeService.getStoreDtoById(id);
        if (store == null) {
            return "redirect:/partners/stores";
        }
        model.addAttribute("storeDTO", store);
        model.addAttribute("categories", storeCategoryRepository.findAll());
        return "partners/stores/edit";
    }

    // 매장 정보 업데이트
    @PostMapping("/update")
    public String updateStore(
            @ModelAttribute StoreDTO storeDTO
    ) {
        storeService.updateStore(storeDTO);
        return "redirect:/partners/stores";
    }

    // 매장 삭제 처리
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok().build();
    }

}
