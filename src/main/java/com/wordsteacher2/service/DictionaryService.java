package com.wordsteacher2.service;

import com.wordsteacher2.dto.DictionaryDto;
import com.wordsteacher2.dto.DictionaryListWithAdvancementDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DictionaryService {
    List<DictionaryDto> getWords(String type);

    DictionaryListWithAdvancementDto addWord(DictionaryDto dictionaryDto);

    List<DictionaryDto> deleteWord(DictionaryDto dictionaryDto);

    List<DictionaryDto> editWord(List<DictionaryDto> dictionaryDtos);
}
