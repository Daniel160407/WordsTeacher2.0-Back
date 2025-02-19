ALTER TABLE `wordsteacher`.`dictionary`
ADD COLUMN `language_id` INT NOT NULL AFTER `user_id`;

ALTER TABLE `wordsteacher`.`level`
ADD COLUMN `language_id` INT NOT NULL AFTER `user_id`;

ALTER TABLE `wordsteacher`.`statistics`
ADD COLUMN `language_id` INT NOT NULL AFTER `user_id`;

ALTER TABLE `wordsteacher`.`words`
ADD COLUMN `language_id` INT NOT NULL AFTER `user_id`;
