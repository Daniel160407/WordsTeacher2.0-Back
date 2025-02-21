package com.wordsteacher2.service;

import com.wordsteacher2.dto.LanguageDto;
import com.wordsteacher2.model.Language;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LanguagesService {
    List<LanguageDto> getUserLanguages(Integer userId);

    Integer getLanguageId(String language, Integer userId);

    List<LanguageDto> addLanguage(LanguageDto languageDto);

    List<LanguageDto> addFirstLanguage(LanguageDto languageDto);

    List<LanguageDto> removeLanguage(String language, Integer userId);
}
