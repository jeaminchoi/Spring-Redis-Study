package sparta.study.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.study.api.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // --------------실험 1 인덱스 기초 효과 증명--------------

    // 실험 1-1. 인덱스 유무에 따른 실행계획 변화
    public String getProductByIdNoIndex(String productName) {
        return "noIndex";
    }

    // 실험 1-2. 인덱스 없는 필드 검색
    public String getProductByIdIndex(String productName) {
        return "index";
    }

    // --------------실험 2 옵티마이저가 인덱스를 포기하는 조건--------------


    // --------------실험 3 복합 인덱스 설계--------------


    // --------------실험 4 Enum 인덱싱--------------

    // --------------실험 5 연관관계 인덱싱--------------

    // --------------실험 6 과도한 인덱스의 역효과--------------

    // --------------실험 7: 적정 인덱스 결정 프로세스--------------

    // --------------캐싱 검색 성능 측정--------------

    // 단순 로컬 캐시
    public String getProductByIdLocalCache(String productName) {
        return "local-cache";
    }

    // 레디스 캐시
    public String getProductByIdRedisCache(String productName) {
        return "redis-cache";
    }
}
