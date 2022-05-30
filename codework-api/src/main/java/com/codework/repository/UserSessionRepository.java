package com.codework.repository;

import com.codework.entity.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserSessionRepository extends MongoRepository<UserSession, Long>{
    Optional<UserSession> findTop1ByUserIdOrderByLoginTimeDesc(Long userId);
}
