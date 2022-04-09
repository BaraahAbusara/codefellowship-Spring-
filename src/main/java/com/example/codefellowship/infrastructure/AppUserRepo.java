package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepo extends CrudRepository<ApplicationUser,Integer> {
    ApplicationUser findByUsername(String username);

}
