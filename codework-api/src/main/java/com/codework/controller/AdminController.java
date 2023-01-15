package com.codework.controller;

import com.codework.entity.Language;
import com.codework.model.LanguageBatch;
import com.codework.model.Response;
import com.codework.service.ILanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "api/admin")
@RestController
@Slf4j
public class AdminController {

	@Autowired
	private ILanguageService languageService;

	@PostMapping(value = "language/batch")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<List<Language>> createLanguages(@RequestBody LanguageBatch languageBatch) {
		log.info("createLanguages "+languageBatch);
		List<Language> languageList = languageService.createLanguages(languageBatch);
		return new Response<>(languageList);
	}

	@PutMapping(value = "language/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<Language> updateLanguage(@PathVariable String id, @RequestBody Language language) {
		log.info("updateLanguage "+language);
		return new Response<>(languageService.updateLanguage(language));
	}

	@GetMapping(value = "language/list")
	public Response<List<Language>> getLanguageList() {
		return new Response<>(languageService.getActiveLanguages());
	}

}
