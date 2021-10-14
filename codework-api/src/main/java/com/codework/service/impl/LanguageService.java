package com.codework.service.impl;

import com.codework.entity.Language;
import com.codework.entity.Problem;
import com.codework.model.LanguageBatch;
import com.codework.model.ProblemDetails;
import com.codework.repository.LanguageRepository;
import com.codework.repository.ProblemRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.ILanguageService;
import com.codework.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageService implements ILanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	private List<Language> languageList; // cache

	@Override
	public List<Language> createLanguages(LanguageBatch languageBatch) {
		languageList = languageRepository.saveAll(languageBatch.getLanguages());
		return languageList;
	}

	@Override
	public Language updateLanguage(Language language) {
		Language updatedLanguage = languageRepository.save(language);
		languageList = null;
		return updatedLanguage;
	}

	@Override
	public Language getLanguage(String id) {
		return languageRepository.findById(id).get();
	}

	@Override
	public void deleteLanguage(String id) {
		Language language = getLanguage(id);
		languageRepository.delete(language);
	}

	@Override
	public List<Language> getActiveLanguages() {
		if(languageList!=null){
			return languageList;
		}
		languageList = languageRepository.findAll();
		return languageList;
	}
}
