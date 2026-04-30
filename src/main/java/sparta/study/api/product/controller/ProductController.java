package sparta.study.api.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.study.api.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/v1/NoIndex")
    public ResponseEntity<String> getProductById(@RequestParam String productName) {
        return ResponseEntity.ok(productService.getProductByIdNoIndex(productName));
    }
}
