SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `proposta` DEFAULT CHARACTER SET utf8 ;
USE `proposta` ;

-- -----------------------------------------------------
-- Table `proposta`.`proposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proposta`.`proposta` (
     `proposta_id` BINARY(16) NOT NULL,
     `cpf_cnpj` VARCHAR(14) NOT NULL,
     `email` VARCHAR(30) NOT NULL,
     `nome` VARCHAR(160) NOT NULL,
     `endereco` VARCHAR(100) NOT NULL,
     `salario` DECIMAL(10,2) NOT NULL,
     PRIMARY KEY (`proposta_id`),
     UNIQUE INDEX `cpf_cnpj_UNIQUE` (`cpf_cnpj` ASC) VISIBLE,
     UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
