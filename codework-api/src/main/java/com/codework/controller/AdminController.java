package com.codework.controller;

import com.codework.entity.Language;
import com.codework.model.LanguageBatch;
import com.codework.model.Response;
import com.codework.service.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/admin")
@RestController
public class AdminController {

	@Autowired
	private ILanguageService languageService;

	@PostMapping(value = "language/batch")
	public Response<List<Language>> createLanguages(@RequestBody LanguageBatch languageBatch) {
		List<Language> languageList = languageService.createLanguages(languageBatch);
		return new Response<>(languageList);
	}

	@PutMapping(value = "language/{id}")
	public Response<Language> updateLanguage(@PathVariable String id, @RequestBody Language language) {
		return new Response<>(languageService.updateLanguage(language));
	}

	@GetMapping(value = "language/list")
	public Response<List<Language>> getLanguageList() {
		return new Response<>(languageService.getActiveLanguages());
	}

}
