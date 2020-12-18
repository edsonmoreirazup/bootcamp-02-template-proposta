CREATE TABLE IF NOT EXISTS `proposta`.`cartao_bloqueado` (
     `cartao_bloqueado_id` BINARY(16) NOT NULL,
     `data_bloqueio` DATETIME NOT NULL,
     `request_ip` VARCHAR(45) NOT NULL,
     `request_user_agent` VARCHAR(45) NOT NULL,
     `cartao_id` BINARY(16) NOT NULL,
     PRIMARY KEY (`cartao_bloqueado_id`),
     INDEX `fk_cartao_bloqueado_cartao_idx` (`cartao_id` ASC) VISIBLE,
     CONSTRAINT `fk_cartao_bloqueado_cartao`
         FOREIGN KEY (`cartao_id`)
             REFERENCES `proposta`.`cartao` (`cartao_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION)
ENGINE = InnoDB;
