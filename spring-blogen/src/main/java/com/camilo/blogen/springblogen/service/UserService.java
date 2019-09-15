package com.camilo.blogen.springblogen.service;

import com.camilo.blogen.springblogen.model.User;
import com.camilo.blogen.springblogen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> getAllUsers(){
        List<User> result = (List<User>) repository.findAll();

        if(result.size() > 0){
            return result;
        }
        else {
            return new ArrayList<User>();
        }
    }

    public User addUser(User entity){
        entity = repository.save(entity);
        return entity;
    }

    public User getUser(String entity){
        List<User> result = (List<User>) repository.findAll();
        for(User user : result){
            if (user.getUsername().equals(entity)){
                return user;
            }
        }
        return null;
    }

    public void deleteUser(String entity){
        List<User> result = (List<User>) repository.findAll();
        for(User user : result){
            if (user.getUsername().equals(entity)){
                repository.delete(user);
            }
        }
    }
}
