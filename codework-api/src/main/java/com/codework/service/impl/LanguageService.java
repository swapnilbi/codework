package com.codework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.Language;
import com.codework.model.LanguageBatch;
import com.codework.repository.LanguageRepository;
import com.codework.service.ILanguageService;

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
	public Language getLanguage(Integer id) {
		return languageRepository.findById(id).get();
	}

	@Override
	public void deleteLanguage(Integer id) {
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

	@Override
	public List<Language> getLanguages(List<Integer> languages) {
		return languageRepository.findByIds(languages);
	}
}
