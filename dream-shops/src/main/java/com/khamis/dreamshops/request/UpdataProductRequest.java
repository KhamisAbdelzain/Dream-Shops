package com.khamis.dreamshops.request;

import com.khamis.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdataProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
