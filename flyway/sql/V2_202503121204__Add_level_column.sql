ALTER TABLE `wordsteacher`.`words`
ADD COLUMN `level` VARCHAR(45) NOT NULL AFTER `active`;

ALTER TABLE `wordsteacher`.`dictionary`
ADD COLUMN `level` VARCHAR(45) NOT NULL AFTER `meaning`;
