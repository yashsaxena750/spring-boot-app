package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getById(long id) {

        return postRepository.findById(id);
    }

    public List<Post> getAll() {

        return postRepository.findAll();
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Post save(Post post) {
        if(post.getId() == null)
            post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }
}
