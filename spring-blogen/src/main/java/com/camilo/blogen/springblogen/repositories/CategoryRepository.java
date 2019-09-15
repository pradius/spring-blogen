package com.camilo.blogen.springblogen.repositories;

import com.camilo.blogen.springblogen.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
