package com.wordsteacher2.service;

import com.wordsteacher2.model.Crossword;
import com.wordsteacher2.model.Word;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.service.direction.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CrosswordService {
    private final WordsRepository wordsRepository;
    private static final Random random = new Random();

    @Autowired
    public CrosswordService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public List<Crossword> generateCrossword(Integer userId, Integer languageId) {
        List<Word> words = wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("word", "true", userId, languageId);
        List<Crossword> crosswords = new ArrayList<>();
        for (Word word : words) {
            crosswords.add(new Crossword(word.getWord(), word.getWord().length(), null, null, 0));
        }

        for (int i = 1; i < words.size(); i++) {
            List<Integer> firstWordMatchedIndexes = new ArrayList<>();
            List<Integer> secondWordMatchedIndexes = new ArrayList<>();

            String firstWord = words.get(i - 1).getWord();
            String secondWord = words.get(i).getWord();
            for (int j = 0; j < firstWord.length(); j++) {
                for (int k = 0; k < secondWord.length(); k++) {
                    if (firstWord.getBytes()[j] == secondWord.getBytes()[k]) {
                        firstWordMatchedIndexes.add(j);
                        secondWordMatchedIndexes.add(k);
                    }
                }
            }

            int firstWordCrossingRandomIndex = firstWordMatchedIndexes.get(random.nextInt(firstWordMatchedIndexes.size()));
            int secondWordCrossingRandomIndex = secondWordMatchedIndexes.get(random.nextInt(secondWordMatchedIndexes.size()));

            Direction direction;
            if (i == 1) {
                direction = Direction.COLUMN;
                crosswords.get(i).setDirection(direction);
                crosswords.get(i).setCrossIndex(firstWordCrossingRandomIndex);
            } else if (crosswords.get(i - 1).getDirection() == Direction.COLUMN) {
                direction = Direction.ROW;
            } else {
                direction = Direction.COLUMN;
            }

            crosswords.get(i).setDirection(direction);
            crosswords.get(i).setCrossIndex(secondWordCrossingRandomIndex);
        }
        System.out.println(crosswords);
        return crosswords;
    }
}