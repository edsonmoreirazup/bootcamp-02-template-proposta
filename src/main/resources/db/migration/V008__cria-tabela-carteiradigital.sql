CREATE TABLE IF NOT EXISTS `proposta`.`carteira_digital` (
     `carteira_digital_id` BINARY(16) NOT NULL,
     `cartao_id` BINARY(16) NOT NULL,
     `email` VARCHAR(254) NOT NULL,
     `provedor` VARCHAR(45) NOT NULL,
     PRIMARY KEY (`carteira_digital_id`),
     INDEX `fk_carteira_digital_cartao_idx` (`cartao_id` ASC) VISIBLE,
     CONSTRAINT `fk_carteira_digital_cartao`
         FOREIGN KEY (`cartao_id`)
             REFERENCES `proposta`.`cartao` (`cartao_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION)
ENGINE = InnoDB;