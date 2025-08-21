package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.request.AddProductRequest;
import com.khamis.dreamshops.request.UpdataProductRequest;
import com.khamis.dreamshops.dto.ProductDto;
import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.Product;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.service.productService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto=productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Seccess", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> productDtos = productService.ConvertedDtoProducts(products);
            return ResponseEntity.ok(new ApiResponse("Seccess", productDtos));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all/{name}")
    public ResponseEntity<ApiResponse> getAllProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            List<ProductDto> productDtos = productService.ConvertedDtoProducts(products);

            return ResponseEntity.ok(new ApiResponse("Seccess", productDtos));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> getAllProductsByName(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Delete", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product productRequest = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse(" Add Product Success", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdataProductRequest productRequest, @PathVariable Long id) {
        try {
            Product product = productService.updateProduct(productRequest, id);
            ProductDto productDto=productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success Update", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found Products", null));
            }
            List<ProductDto> productDtos = productService.ConvertedDtoProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", null));
        }


    }

    @GetMapping("product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found Products", null));
            }
            List<ProductDto> productDtos = productService.ConvertedDtoProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", null));
        }

    }
    @GetMapping("product/{category}/product")
    public ResponseEntity<ApiResponse>getProductsByCategory(@PathVariable String category){
        try {
            List<Product>products=productService.getProductsByCategory(category);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found product",null));
            }
            List<ProductDto> productDtos = productService.ConvertedDtoProducts(products);

            return ResponseEntity.ok(new ApiResponse("Success",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error in Server",null));
        }
    }
    @GetMapping("product/brands")
    public ResponseEntity<ApiResponse>getProductsByBrand(@RequestParam String brand){
        try {
            List<Product>products=productService.getProductsByBrand(brand);

            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found product",null));
            }
            List<ProductDto> productDtos = productService.ConvertedDtoProducts(products);

            return ResponseEntity.ok(new ApiResponse("Success",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error in Server",null));
        }
    }
    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse>countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            var productCount=productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("Products Count ",productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }


    }
}