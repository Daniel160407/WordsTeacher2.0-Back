package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.User;
import com.wordsteacher2.model.Word;
import com.wordsteacher2.repository.LevelRepository;
import com.wordsteacher2.repository.UsersRepository;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordsServiceImpl implements WordsService {
    private final WordsRepository wordsRepository;
    private final LevelRepository levelRepository;
    private final UsersRepository usersRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public WordsServiceImpl(WordsRepository wordsRepository, LevelRepository levelRepository, UsersRepository usersRepository, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.levelRepository = levelRepository;
        this.usersRepository = usersRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> getWords(String wordsType, Integer userId, Integer languageId, Boolean tests) {
        if (tests) {
            Optional<User> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent() && !userOptional.get().getPlan().equals("free")) {
                return modelConverter.convertWordsToDtoList(
                        wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId(wordsType, "true", userId, languageId));
            } else {
                throw new NoPermissionException();
            }
        }
        return modelConverter.convertWordsToDtoList(
                wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId(wordsType, "true", userId, languageId));
    }

    @Override
    public List<WordDto> addWord(WordDto wordDto) {
        wordDto.setActive("true");
        wordsRepository.save(modelConverter.convert(wordDto));
        return modelConverter.convertWordsToDtoList(
                wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId(wordDto.getWordType(), "true", wordDto.getUserId(), wordDto.getLanguageId()));
    }

    @Override
    public Level getLevel(Integer userId, Integer languageId) {
        return levelRepository.findByUserIdAndLanguageId(userId, languageId);
    }

    @Override
    public List<WordDto> changeWord(List<WordDto> wordDtos) {
        WordDto original = wordDtos.get(0);
        WordDto changed = wordDtos.get(1);

        Word foundWord = wordsRepository.findByWordAndMeaningAndUserIdAndLanguageId(original.getWord(), original.getMeaning(), original.getUserId(), original.getLanguageId());
        foundWord.setWord(changed.getWord());
        foundWord.setMeaning(changed.getMeaning());
        foundWord.setWordType(changed.getWordType());

        wordsRepository.save(foundWord);
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId(
                original.getWordType(), "true", original.getUserId(), original.getLanguageId()));
    }

    @Override
    public List<WordDto> deleteWord(WordDto wordDto) {
        wordsRepository.deleteByWordAndMeaningAndUserIdAndLanguageId(wordDto.getWord(), wordDto.getMeaning(), wordDto.getUserId(), wordDto.getLanguageId());
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId(
                wordDto.getWordType(), "true", wordDto.getUserId(), wordDto.getLanguageId()));
    }
}
