package com.khamis.dreamshops.repository.imageRepository;

import com.khamis.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProductId(long id);
}
