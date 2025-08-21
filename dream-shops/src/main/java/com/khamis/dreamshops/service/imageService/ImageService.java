package com.khamis.dreamshops.service.imageService;

import com.khamis.dreamshops.dto.ImageDto;
import com.khamis.dreamshops.exceptions.ImageNotFoundException;
import com.khamis.dreamshops.model.Image;
import com.khamis.dreamshops.model.Product;
import com.khamis.dreamshops.repository.imageRepository.ImageRepository;
import com.khamis.dreamshops.service.productService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ImageNotFoundException("The image Not FOUND")) ;}

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ImageNotFoundException("the image not exists "+id);
        });

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product=productService.getProductById(productId);
        List<ImageDto>savedImageDto=new ArrayList<>();
        for(MultipartFile file:files){
        try {
            Image image=new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setProduct(product);
            String buildDownLoadUrl="/api/v1/images/image/download/";
            String downLoadUrl=buildDownLoadUrl+image.getId();
            image.setDownloadUrl(downLoadUrl);
            Image savedImage=imageRepository.save(image);

            savedImage.setDownloadUrl(buildDownLoadUrl+savedImage.getId());
            imageRepository.save(savedImage);

            ImageDto imageDto=new ImageDto();
            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());
            savedImageDto.add(imageDto);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }}
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile multipartFile, Long imageId) {
    Image image=getImageById(imageId);
        try {
            image.setFileName(multipartFile.getOriginalFilename());
            image.setFileName(multipartFile.getOriginalFilename());
            image.setImage(new SerialBlob(multipartFile.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
