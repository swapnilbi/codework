package com.codework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
			return new ArrayList<>(languageList);
		}
		languageList = languageRepository.findAll();
		return new ArrayList<>(languageList);
	}

	@Override
	public List<Language> getLanguages(List<Integer> languageIds) {
		List<Language> languages = this.getActiveLanguages();
		return languages.stream()
        .distinct()
        .filter(p -> languageIds.contains(p.getId()))
        .collect(Collectors.toList());
	}
}
