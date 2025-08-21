package com.khamis.dreamshops.repository.categoryRepo;

import com.khamis.dreamshops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatgoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);

    Object existsByName(String name);
}
