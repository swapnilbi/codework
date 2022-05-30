package com.codework.repository;

import com.codework.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
