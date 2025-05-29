package com.wordsteacher2.service;

import com.wordsteacher2.dto.UserDto;
import com.wordsteacher2.freemius.model.PlanWithLanguageId;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.model.Language;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.model.User;
import com.wordsteacher2.repository.*;
import com.wordsteacher2.service.exception.InvalidEmailOrPasswordException;
import com.wordsteacher2.service.exception.UserAlreadyRegisteredException;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final LevelRepository levelRepository;
    private final StatisticsRepository statisticsRepository;
    private final LanguagesRepository languagesRepository;
    private final WordsRepository wordsRepository;
    private final DictionaryRepository dictionaryRepository;
    private final ModelConverter modelConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, LevelRepository levelRepository, StatisticsRepository statisticsRepository, LanguagesRepository languagesRepository, WordsRepository wordsRepository, DictionaryRepository dictionaryRepository, ModelConverter modelConverter, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.levelRepository = levelRepository;
        this.statisticsRepository = statisticsRepository;
        this.languagesRepository = languagesRepository;
        this.wordsRepository = wordsRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.modelConverter = modelConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PlanWithLanguageId logIn(UserDto userDto) {
        User user = usersRepository.findByEmail(userDto.getEmail());
        if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new InvalidEmailOrPasswordException();
        } else {
            return new PlanWithLanguageId(user.getId(), user.getPlan(), languagesRepository.findFirstByUserId(user.getId()).getId());
        }
    }

    @Override
    public Integer register(UserDto userDto) {
        if (usersRepository.findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDto.setPlan("free");
            usersRepository.save(modelConverter.convert(userDto));

            Integer userId = usersRepository.findByEmail(userDto.getEmail()).getId();
            languagesRepository.save(new Language(userDto.getLanguage(), userId));
            Integer languageId = languagesRepository.findFirstByUserId(userId).getId();
            Level level = new Level(1, userId, languageId);
            levelRepository.save(level);

            Statistic statistic = new Statistic(0, 0, 0, "", "", userId, languageId);
            statisticsRepository.save(statistic);

            return languagesRepository.findFirstByUserId(userId).getId();
        } else {
            throw new UserAlreadyRegisteredException();
        }
    }

    @Override
    public void deleteAccount(Integer userId) {
        if (usersRepository.findById(userId).isPresent()) {
            usersRepository.deleteById(userId);
            wordsRepository.deleteAllByUserId(userId);
            dictionaryRepository.deleteAllByUserId(userId);
            levelRepository.deleteAllByUserId(userId);
            statisticsRepository.deleteAllByUserId(userId);
            languagesRepository.deleteAllByUserId(userId);
        } else {
            throw new NoPermissionException();
        }
    }
}
