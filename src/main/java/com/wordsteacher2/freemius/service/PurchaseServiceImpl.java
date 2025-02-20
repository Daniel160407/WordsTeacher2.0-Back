package com.wordsteacher2.freemius.service;

import com.wordsteacher2.dto.UserDto;
import com.wordsteacher2.freemius.model.Plan;
import com.wordsteacher2.model.User;
import com.wordsteacher2.repository.UsersRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final UsersRepository usersRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public PurchaseServiceImpl(UsersRepository usersRepository, ModelConverter modelConverter) {
        this.usersRepository = usersRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public void changePlan(Plan plan) {
        Optional<User> userOptional = usersRepository.findById(plan.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPlan(plan.getPlan());
            usersRepository.save(user);
        }
    }

    @Override
    public void removePlan(Plan plan) {
        Optional<User> userOptional = usersRepository.findById(plan.getUserId());
        if (userOptional.isPresent()) {
            usersRepository.deleteByIdAndPlan(plan.getUserId(), plan.getPlan());
        }
    }
}
