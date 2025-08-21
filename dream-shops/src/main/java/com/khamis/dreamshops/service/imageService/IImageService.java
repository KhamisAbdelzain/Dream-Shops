package com.khamis.dreamshops.service.imageService;

import com.khamis.dreamshops.dto.ImageDto;
import com.khamis.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> multipartFile, Long productId);
    void updateImage(MultipartFile multipartFile,Long imageId);
}
