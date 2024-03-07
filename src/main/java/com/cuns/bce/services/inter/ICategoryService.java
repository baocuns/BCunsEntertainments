package com.cuns.bce.services.inter;

import com.cuns.bce.entities.Category;
import org.springframework.stereotype.Service;

@Service
public interface ICategoryService {
    // get category by id
    Category getCategoryById(Long id);
}
