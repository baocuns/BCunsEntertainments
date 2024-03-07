package com.cuns.bce.services.impl;

import com.cuns.bce.entities.Category;
import com.cuns.bce.repositories.CategoryRepository;
import com.cuns.bce.services.inter.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    final private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
