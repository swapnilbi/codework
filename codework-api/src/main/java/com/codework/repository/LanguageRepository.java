package com.codework.repository;

import com.codework.entity.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LanguageRepository extends MongoRepository<Language, Integer>{
	@Query("{ '_id' : { '$in': ?0 } })")
    List<Language> findByIds(List<Integer> ids);
}
