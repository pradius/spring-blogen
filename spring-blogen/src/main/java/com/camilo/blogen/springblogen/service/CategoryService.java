package com.camilo.blogen.springblogen.service;

import com.camilo.blogen.springblogen.model.Category;
import com.camilo.blogen.springblogen.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;

    public List<Category> getAllCategories(){
        List<Category> result = (List<Category>) repository.findAll();

        if(result.size() > 0){
            return result;
        }
        else {
            return new ArrayList<Category>();
        }
    }

    public Category addCategory (Category entity){
        entity = repository.save(entity);
        return entity;
    }
}
