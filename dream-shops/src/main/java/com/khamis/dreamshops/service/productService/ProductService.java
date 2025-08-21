package com.khamis.dreamshops.service.productService;

import com.khamis.dreamshops.request.AddProductRequest;
import com.khamis.dreamshops.request.UpdataProductRequest;
import com.khamis.dreamshops.dto.ImageDto;
import com.khamis.dreamshops.dto.ProductDto;
import com.khamis.dreamshops.exceptions.ProductNotFoundException;
import com.khamis.dreamshops.model.Category;
import com.khamis.dreamshops.model.Image;
import com.khamis.dreamshops.model.Product;
import com.khamis.dreamshops.repository.ProductRepo.ProductRepository;
import com.khamis.dreamshops.repository.categoryRepo.CatgoryRepository;
import com.khamis.dreamshops.repository.imageRepository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CatgoryRepository catgoryRepository;
    private final ImageRepository imageRepository;


    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {

        if(productExists(request.getName(),request.getBrand())){
            throw new ProductNotFoundException("The product"+request.getName()+"and"+request.getBrand()+"alreadyExists");
        }


        Category category= Optional.ofNullable(catgoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                        Category cat=new Category(request.getCategory().getName());
                        return catgoryRepository.save(cat);
                        }
                );
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));

    }
    private Product createProduct(AddProductRequest request, Category category){
       return new Product(
           request.getName(),
           request.getBrand(),
           request.getPrice(),
           request.getInventory(),
           request.getDescription(),
           category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete
                        ,()->{
                            throw new ProductNotFoundException("Product Not Found");
                        });

    }

    @Override
    public Product updateProduct(UpdataProductRequest product, Long productId) {
    return productRepository.findById(productId)
            .map(product1 -> updateProductDetail(product1,product))
            .map(productRepository::save)
            .orElseThrow(()->new ProductNotFoundException("Not Found "));

    }
    private boolean productExists(String name,String brand){
        return productRepository.existsByNameAndBrand(name,brand);
    }
    private Product updateProductDetail(Product product, UpdataProductRequest updataProductRequest){
        product.setName(updataProductRequest.getName());
        product.setBrand(updataProductRequest.getBrand());
        product.setDescription(updataProductRequest.getDescription());
        product.setInventory(updataProductRequest.getInventory());
        product.setPrice(updataProductRequest.getPrice());
        Category category=catgoryRepository.findByName(updataProductRequest.getCategory().getName());
        product.setCategory(category);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
@Override
public List<ProductDto>ConvertedDtoProducts(List<Product> products){

        return products.stream().map(this::convertToDto).toList();

    }





    @Override
    public ProductDto convertToDto(Product product){
    ProductDto productDto=modelMapper.map(product,ProductDto.class);
    List<Image>images=imageRepository.findByProductId(product.getId());
    List<ImageDto>imageDtos=images.stream()
            .map(image -> modelMapper.map(image,ImageDto.class))
            .toList();

    productDto.setImages(imageDtos);
    return productDto;

    }
}
