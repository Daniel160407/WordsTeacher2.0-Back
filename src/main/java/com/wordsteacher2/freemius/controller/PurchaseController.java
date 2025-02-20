package com.wordsteacher2.freemius.controller;

import com.wordsteacher2.freemius.model.Plan;
import com.wordsteacher2.freemius.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PutMapping
    public ResponseEntity<?> changePlan(@RequestBody Plan plan) {
        purchaseService.changePlan(plan);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<?> removePlan(@RequestParam Integer userid, @RequestParam String plan) {
        purchaseService.removePlan(new Plan(userid, plan));
        return ResponseEntity.ok().build();
    }
}
