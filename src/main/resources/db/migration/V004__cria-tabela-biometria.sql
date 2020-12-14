CREATE TABLE IF NOT EXISTS `proposta`.`biometria` (
  `biometria_id` BINARY(16) NOT NULL,
  `fingerprint` BLOB NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `cartao_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`biometria_id`),
  INDEX `fk_biometria_cartao1_idx` (`cartao_id` ASC) VISIBLE,
  CONSTRAINT `fk_biometria_cartao1`
      FOREIGN KEY (`cartao_id`)
          REFERENCES `proposta`.`cartao` (`cartao_id`)
          ON DELETE NO ACTION
          ON UPDATE NO ACTION)
ENGINE = InnoDB;