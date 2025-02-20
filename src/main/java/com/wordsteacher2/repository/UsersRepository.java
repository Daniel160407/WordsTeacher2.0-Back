package com.wordsteacher2.repository;

import com.wordsteacher2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    Optional<User> findById(Integer id);

    @Transactional
    void deleteByIdAndPlan(Integer id, String plan);
}
