ALTER TABLE `wordsteacher`.`statistics`
ADD COLUMN `last_activity` VARCHAR(45) NOT NULL AFTER `language_id`;