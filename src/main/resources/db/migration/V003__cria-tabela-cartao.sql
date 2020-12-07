CREATE TABLE IF NOT EXISTS `proposta`.`cartao` (
   `cartao_id` BINARY(16) NOT NULL,
   `numero` VARCHAR(19) NOT NULL,
   `proposta_id` BINARY(16) NOT NULL,
   PRIMARY KEY (`cartao_id`),
   INDEX `fk_cartao_proposta_idx` (`proposta_id` ASC) VISIBLE,
   CONSTRAINT `fk_cartao_proposta`
       FOREIGN KEY (`proposta_id`)
           REFERENCES `proposta`.`proposta` (`proposta_id`)
           ON DELETE NO ACTION
           ON UPDATE NO ACTION)
ENGINE = InnoDB;