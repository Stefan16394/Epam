package com.vmzone.demo.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmzone.demo.dto.AddCategoryDTO;
import com.vmzone.demo.dto.ListCategory;
import com.vmzone.demo.dto.ListFinalSubCategories;
import com.vmzone.demo.dto.ListSubCategory;
import com.vmzone.demo.exceptions.ResourceAlreadyExistsException;
import com.vmzone.demo.models.Category;
import com.vmzone.demo.repository.CategoryRepository;

/**
 * Service layer communicating with category repository for managing categories requests
 *
 * @author Stefan Rangelov and Sabiha Djurina
 */

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ListCategory> getAllMainCategories() {

        List<ListCategory> mainCategories = this.categoryRepository.findByParentIsNull().stream()
                .map(c -> new ListCategory(c.getCategoryId(), c.getName(),
                        c.getParent(), this.getSubcategoriesForCategory(c.getCategoryId())))
                .collect(Collectors.toList());

        return mainCategories;
    }


    public Category createCategory(AddCategoryDTO category) throws ResourceAlreadyExistsException {
        Category checkExists = this.categoryRepository.findByName(category.getName());
        if (checkExists != null) {
            throw new ResourceAlreadyExistsException("This category already exists!");
        }
        Category newCategory = new Category(category.getName(), category.getParent_id() == null ? null
                : this.categoryRepository.findById(category.getParent_id()).get());
        return this.categoryRepository.save(newCategory);
    }

    public List<ListSubCategory> getSubcategoriesForCategory(long id) {
        return this.categoryRepository.findByParentCategoryId(id)
                .stream()
                .map(subCat -> new ListSubCategory(subCat.getCategoryId(), subCat.getName()))
                .collect(Collectors.toList());
    }

    /**
     * get all subcategories for a category
     *
     * @param id - id of category object stored in db
     * @return list of dto objects
     */
    public List<ListFinalSubCategories> getLeafCategories(long id) {
        List<Category> categories = this.categoryRepository.findByParentCategoryId(id);
        List<ListFinalSubCategories> subCats = new ArrayList<>();
        for (Category c : categories) {
            Category parent = c.getParent();
            Deque<String> paths = new ArrayDeque<String>();
            while (parent != null) {
                paths.push(parent.getName());
                parent = parent.getParent();
            }
            paths.addLast(c.getName());

            subCats.add(new ListFinalSubCategories(c.getCategoryId(), String.join(" -> ", paths)));
        }
        return subCats;
    }
}
