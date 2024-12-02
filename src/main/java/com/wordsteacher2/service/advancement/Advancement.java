package com.wordsteacher2.service.advancement;

import lombok.Getter;

public enum Advancement {
    // Words advancements (Lore-inspired descriptions)
    TENWORDS("The First Step (Learn 10 words)"),
    FIFTYWORDS("The Path of Knowledge (Learn 50 words)"),
    HUNDREDWORDS("The Scholar's Journey (Learn 100 words)"),
    FIVEHUNDREDWORDS("Master of Words (Learn 500 words)"),
    THOUSANDWORDS("The Wordsmith (Learn 1,000 words)"),
    FIVETHOUSANDWORDS("The Polyglot (Learn 5,000 words)"),

    // Cycle advancements (Lore-inspired descriptions)
    ONECYCLESTREAK("The Initiate's Cycle (1 cycle streak)"),
    FIVECYCLESSTREAK("Adept's Cycle (5 cycles streak)"),
    TENCYCLESSTREAK("Master of Cycles (10 cycles streak)"),
    TWENTYCYCLESSTREAK("Wizard of Cycles (20 cycles streak)"),
    FIFTYCYCLESSTREAK("Grandmaster of Cycles (50 cycles streak)"),

    // Day streak advancements (Lore-inspired descriptions)
    WEEKSTREAK("Rising Star (1 week streak)"),
    TWOWEEKSTREAK("Ascendant (2 weeks streak)"),
    THREEWEEKSTREAK("Immortal (3 weeks streak)"),
    MONTHSTREAK("Legendary Learner (1 month streak)"),
    TWOMONTHSTREAK("Hero of Knowledge (2 months streak)"),
    SIXMONTHSTREAK("Titan of Wisdom (6 months streak)"),
    ONEYEARSTREAK("Eternal Sage (1 year streak)");

    @Getter
    private final String description;

    Advancement(String description) {
        this.description = description;
    }
}
