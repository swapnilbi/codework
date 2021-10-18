package com.codework.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.codework.entity.Language;

public interface LanguageRepository extends MongoRepository<Language, Integer>{
	@Query("{_id: { $in: ?0 } })")
    List<Language> findByIds(List<Integer> ids);
}
