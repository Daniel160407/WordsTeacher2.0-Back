package com.wordsteacher2.freemius.service;

import com.wordsteacher2.freemius.model.Plan;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {
    void changePlan(Plan plan);

    void removePlan(Plan plan);
}
