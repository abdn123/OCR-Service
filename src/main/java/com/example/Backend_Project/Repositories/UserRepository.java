package com.example.Backend_Project.Repositories;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend_Project.Entities.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
    @Query("select * from User where username=(?1)")
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long ID);

    long countByActive(boolean active);
}