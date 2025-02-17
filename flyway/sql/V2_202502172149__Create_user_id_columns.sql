ALTER TABLE `wordsteacher`.`words`
ADD COLUMN `user_id` INT NOT NULL AFTER `active`;

ALTER TABLE `wordsteacher`.`level`
ADD COLUMN `user_id` INT NOT NULL AFTER `level`;

ALTER TABLE `wordsteacher`.`statistics`
ADD COLUMN `user_id` INT NOT NULL AFTER `day_streak`;

ALTER TABLE `wordsteacher`.`dictionary`
ADD COLUMN `user_id` INT NOT NULL AFTER `meaning`;
