package sparta.study.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.study.product.service.ProductService;

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
