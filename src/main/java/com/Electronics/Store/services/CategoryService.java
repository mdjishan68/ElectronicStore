package com.Electronics.Store.services;

import com.Electronics.Store.dtos.CategoryDto;
import com.Electronics.Store.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto Create(CategoryDto categoryDto);

    //update
    CategoryDto Update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single category details
    CategoryDto get(String categoryId);

    //search category
}
