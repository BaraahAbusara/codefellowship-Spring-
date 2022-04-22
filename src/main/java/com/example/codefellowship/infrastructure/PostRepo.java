package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post,Long> {
    List<Post> findAllByAuthor(String username);

}
