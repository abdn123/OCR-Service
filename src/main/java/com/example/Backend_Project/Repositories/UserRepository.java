package com.example.Backend_Project.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend_Project.Entities.User;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByEmail(String email);
}