package com.wordsteacher2.repository;

import com.wordsteacher2.model.Language;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Integer> {
    List<Language> findAllByUserId(Integer userId);

    Language findFirstByUserId(Integer userId);

    Language findByLanguageAndUserId(String language, Integer userId);

    @Transactional
    List<Language> deleteByLanguageAndUserId(String language, Integer userId);
}
