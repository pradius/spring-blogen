package com.camilo.blogen.springblogen.service;

import com.camilo.blogen.springblogen.model.Post;
import com.camilo.blogen.springblogen.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository repository;

    public List<Post> getAllPosts(){
        List<Post> result = (List<Post>) repository.findAll();

        if(result.size() > 0){
            return result;
        }
        else {
            return new ArrayList<Post>();
        }
    }

    public Post getPostById(Long id){
        Optional<Post> result = repository.findById(id);
        return result.get();
    }

    public Post savePost(Post entity){
        entity = repository.save(entity);
        return entity;
    }

    public Post editPost(Post entity){
        Optional<Post> result = repository.findById(entity.getId());
        Post newPost = result.get();
        newPost.setTitle(entity.getTitle());
        newPost.setCategory(entity.getCategory());
        newPost.setContent(entity.getContent());
        newPost = repository.save(newPost);

        return newPost;
    }

    public void deletePost(Post entity){
        repository.delete(entity);
    }
}
