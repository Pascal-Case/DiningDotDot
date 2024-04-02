package jyang.diningdotdot.dataloader;

import jyang.diningdotdot.entity.store.StoreCategory;
import jyang.diningdotdot.repository.StoreCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataLoader implements CommandLineRunner {
    private final StoreCategoryRepository storeCategoryRepository;

    @Override
    public void run(String... args) {
        List<String> categories = Arrays.asList(
                "한식", "중식", "일식", "양식", "분식", "패스트푸드",
                "베이커리", "카페", "바"
        );

        categories.forEach(categoryName -> {
            storeCategoryRepository.findByName(categoryName)
                    .orElseGet(() -> storeCategoryRepository.save(
                            StoreCategory.builder()
                                    .name(categoryName)
                                    .build()
                    ));

        });
    }
}
