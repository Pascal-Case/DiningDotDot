package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.dto.store.StoreDTO;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import jyang.diningdotdot.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partners/stores")
public class PartnerStoreController {
    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreService storeService;

    @GetMapping
    public String myStoreListPage(Model model) {
        model.addAttribute("storeList", storeService.findStoreListByCurrentPartner());
        model.addAttribute("storeDTO", new StoreDTO());
        model.addAttribute("categories", storeCategoryRepository.findAll());
        return "partners/stores/list";
    }

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

    @PostMapping("/update")
    public String updateStore(
            @ModelAttribute StoreDTO storeDTO
    ) {
        storeService.updateStore(storeDTO);
        return "redirect:/partners/stores";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok().build();
    }

}
