CREATE TABLE `wordsteacher`.`statistics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `learned_words` INT NOT NULL,
  `cycles` INT NOT NULL,
  `day_streak` INT NOT NULL,
  PRIMARY KEY (`id`));
