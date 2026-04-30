package sparta.study.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sparta.study.api.category.entity.Category;
import sparta.study.api.category.repository.CategoryRepository;
import sparta.study.api.product.entity.ProductStatus;
import sparta.study.api.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final int PRODUCT_COUNT = 50_000;
    private static final int BATCH_SIZE = 500;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (categoryRepository.count() > 0) {
            System.out.println("[DataInitializer] 데이터가 이미 존재합니다. 스킵합니다.");
            return;
        }
        System.out.println("[DataInitializer] 카테고리 삽입 시작...");
        List<Category> categories = insertCategories();

        System.out.println("[DataInitializer] 상품 삽입 시작 (" + PRODUCT_COUNT + "건)...");
        insertProducts(categories);

        System.out.println("[DataInitializer] 완료!");
    }

    private List<Category> insertCategories() {
        List<String> names = List.of(
                "전자기기", "패션의류", "식품/음료", "가구/인테리어",
                "도서/음반", "스포츠/레저", "뷰티/미용", "완구/취미",
                "생활용품", "반려동물"
        );
        List<Category> saved = new ArrayList<>();
        for (String name : names) {
            saved.add(categoryRepository.save(Category.builder().name(name).build()));
        }
        return saved;
    }

    private void insertProducts(List<Category> categories) {
        Random random = new Random();
        ProductStatus[] statuses = ProductStatus.values();
        String[] regions = {"서울", "경기", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "강원"};

        String sql = """
                INSERT INTO products (name, description, price, status, seller_region, category_id, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        List<Object[]> batch = new ArrayList<>(BATCH_SIZE);

        for (int i = 1; i <= PRODUCT_COUNT; i++) {
            Category category = categories.get(random.nextInt(categories.size()));
            String status = statuses[random.nextInt(statuses.length)].name();
            String region = regions[random.nextInt(regions.length)];
            int price = (random.nextInt(100) + 1) * 1000; // 1,000원 ~ 100,000원

            batch.add(new Object[]{
                    category.getName() + " 상품 " + i,
                    category.getName() + " 카테고리의 상품입니다. 상품번호: " + i,
                    price,
                    status,
                    region,
                    category.getId(),
                    LocalDateTime.now()
            });

            if (batch.size() == BATCH_SIZE) {
                jdbcTemplate.batchUpdate(sql, batch);
                batch.clear();
                System.out.printf("[DataInitializer] %,d / %,d 삽입 완료%n", i, PRODUCT_COUNT);
            }
        }

        if (!batch.isEmpty()) {
            jdbcTemplate.batchUpdate(sql, batch);
        }
    }
}
