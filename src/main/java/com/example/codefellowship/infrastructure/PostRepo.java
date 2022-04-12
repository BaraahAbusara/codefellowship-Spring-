package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post,Long> {
}
