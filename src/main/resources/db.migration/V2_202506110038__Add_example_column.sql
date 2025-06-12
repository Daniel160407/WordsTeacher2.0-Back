ALTER TABLE `wordsteacher`.`words`
ADD COLUMN `example` LONGTEXT NOT NULL AFTER `meaning`;

ALTER TABLE `wordsteacher`.`dictionary`
ADD COLUMN `example` LONGTEXT NOT NULL AFTER `meaning`;
