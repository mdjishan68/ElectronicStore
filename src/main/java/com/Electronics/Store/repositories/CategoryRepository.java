package com.Electronics.Store.repositories;

import com.Electronics.Store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String>  //<identity , id>

{

}
