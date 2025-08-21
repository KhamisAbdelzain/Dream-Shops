package com.khamis.dreamshops.service.productService;

import com.khamis.dreamshops.request.AddProductRequest;
import com.khamis.dreamshops.request.UpdataProductRequest;
import com.khamis.dreamshops.dto.ProductDto;
import com.khamis.dreamshops.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void  deleteProductById(Long id);
    Product updateProduct(UpdataProductRequest product, Long productId);
    List<Product>getAllProducts();
    List<Product>getProductsByCategory(String category);
    List<Product>getProductsByBrand(String brand);
    List<Product>getProductsByCategoryAndBrand(String category,String brand);
    List<Product>getProductsByName(String name);
    List<Product>getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDto>ConvertedDtoProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
