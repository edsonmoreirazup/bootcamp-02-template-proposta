CREATE TABLE IF NOT EXISTS `proposta`.`aviso_viagem` (
     `aviso_viagem_id` BINARY(16) NOT NULL,
     `cartao_id` BINARY(16) NOT NULL,
     `destino` VARCHAR(60) NOT NULL,
     `data_criacao` DATETIME NOT NULL,
     `data_final` DATE NOT NULL,
     `request_ip` VARCHAR(45) NOT NULL,
     `request_user_agent` VARCHAR(45) NOT NULL,
     PRIMARY KEY (`aviso_viagem_id`),
     INDEX `fk_aviso_viagem_cartao_idx` (`cartao_id` ASC) VISIBLE,
     CONSTRAINT `fk_aviso_viagem_cartao`
         FOREIGN KEY (`cartao_id`)
             REFERENCES `proposta`.`cartao` (`cartao_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION)
ENGINE = InnoDB;