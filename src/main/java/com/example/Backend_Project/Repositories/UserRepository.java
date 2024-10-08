package com.example.backend_project.repositories;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_project.entities.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
    @Query("select * from User where username=(?1)")
    User findByUsername(String username);

    Optional<User> findById(Long id);

    long countByActive(boolean active);
}