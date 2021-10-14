package com.codework.repository;

import com.codework.entity.Challenge;
import com.codework.entity.Language;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LanguageRepository extends MongoRepository<Language, String>{

}
