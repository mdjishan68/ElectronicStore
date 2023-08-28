package com.Electronics.Store.controller;

import com.Electronics.Store.dtos.ApiResponseMessage;
import com.Electronics.Store.dtos.CategoryDto;
import com.Electronics.Store.dtos.PageableResponse;
import com.Electronics.Store.entities.Category;
import com.Electronics.Store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        //call service to create object
        CategoryDto categoryDto1 = categoryService.Create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto){

        CategoryDto updatedCategory = categoryService.Update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId
    ){
        categoryService.delete(categoryId);
        ApiResponseMessage responsemessage = ApiResponseMessage.builder().message("Category is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responsemessage, HttpStatus.OK);

    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>>getAll(
        @RequestParam(value = "pageNumber",defaultValue = "0", required = false)int pageNumber,
        @RequestParam(value = "pageSize",defaultValue = "0", required = false)int pageSize,
        @RequestParam(value = "sortBy",defaultValue = "title", required = false)String sortBy,
        @RequestParam(value = "sortDir",defaultValue = "acs", required = false)String sortDir
    ){
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber,pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    //get single
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

}
