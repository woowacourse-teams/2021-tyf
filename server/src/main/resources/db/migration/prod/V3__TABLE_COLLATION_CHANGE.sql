ALTER TABLE `tyf`.`member`
    CHANGE COLUMN `bio` `bio` VARCHAR(1500) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL ;

ALTER TABLE `tyf`.`donation`
    CHANGE COLUMN `message` `message` VARCHAR(255) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL ;