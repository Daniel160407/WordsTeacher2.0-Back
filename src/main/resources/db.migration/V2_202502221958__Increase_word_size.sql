ALTER TABLE `wordsteacher`.`words`
CHANGE COLUMN `word` `word` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `meaning` `meaning` VARCHAR(100) NOT NULL ;

ALTER TABLE `wordsteacher`.`dictionary`
CHANGE COLUMN `word` `word` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `meaning` `meaning` VARCHAR(100) NOT NULL ;