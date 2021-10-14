package com.codework.service;

import com.codework.entity.Language;
import com.codework.model.LanguageBatch;

import java.util.List;

public interface ILanguageService {

    List<Language> createLanguages(LanguageBatch languageBatch);

    Language updateLanguage(Language language);

    Language getLanguage(String id);

    void deleteLanguage(String id);

    List<Language> getActiveLanguages();
}
