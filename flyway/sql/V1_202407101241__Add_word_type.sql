ALTER TABLE `wordsteacher`.`droppedwords`
    ADD COLUMN `word_type` VARCHAR(45) NOT NULL AFTER `meaning`;
