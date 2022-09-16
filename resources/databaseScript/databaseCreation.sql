-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema community_game
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `community_game` ;

-- -----------------------------------------------------
-- Schema community_game
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `community_game` DEFAULT CHARACTER SET utf8mb4 ;
USE `community_game` ;

-- -----------------------------------------------------
-- Table `community_game`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community_game`.`user` ;

CREATE TABLE IF NOT EXISTS `community_game`.`user` (
  `id` VARCHAR(40) NOT NULL,
  `email` VARCHAR(254) NOT NULL,
  `user_name` VARCHAR(100) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `logged` TINYINT(1) NOT NULL DEFAULT 0,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UserName_UNIQUE` (`user_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `community_game`.`game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community_game`.`game` ;

CREATE TABLE IF NOT EXISTS `community_game`.`game` (
  `id` VARCHAR(40) NOT NULL,
  `title` VARCHAR(1000) NOT NULL,
  `genre` VARCHAR(100) NOT NULL,
  `platform` VARCHAR(100) NOT NULL,
  `description` VARCHAR(3000) NOT NULL,
  `release_date` DATE NULL,
  `image` VARCHAR(1000) NULL,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` VARCHAR(40) NULL,
  INDEX `fk_Game_User1_idx` (`updated_by` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_GAME_USER`
    FOREIGN KEY (`updated_by`)
    REFERENCES `community_game`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `community_game`.`Review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community_game`.`Review` ;

CREATE TABLE IF NOT EXISTS `community_game`.`Review` (
  `game_id` VARCHAR(40) NOT NULL,
  `user_id` VARCHAR(40) NOT NULL,
  `id` VARCHAR(40) NOT NULL,
  `text` VARCHAR(5000) NULL,
  `score` INT NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (`id`),
  INDEX `fk_Game_has_User_User1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Game_has_User_Game_idx` (`game_id` ASC) VISIBLE,
  UNIQUE INDEX `IDX_UNIQUE_GAME_USER` (`game_id` ASC, `user_id` ASC) VISIBLE,
  CONSTRAINT `FK_REVIEW_GAME`
    FOREIGN KEY (`game_id`)
    REFERENCES `community_game`.`game` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_REVIEW_USER`
    FOREIGN KEY (`user_id`)
    REFERENCES `community_game`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `community_game`.`state`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community_game`.`state` ;

CREATE TABLE IF NOT EXISTS `community_game`.`state` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `community_game`.`game_state`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community_game`.`game_state` ;

CREATE TABLE IF NOT EXISTS `community_game`.`game_state` (
  `game_id` VARCHAR(40) NOT NULL,
  `user_id` VARCHAR(40) NOT NULL,
  `state` INT NOT NULL,
  PRIMARY KEY (`game_id`, `user_id`),
  INDEX `fk_Game_has_User_User2_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Game_has_User_Game1_idx` (`game_id` ASC) VISIBLE,
  INDEX `fk_GameStatus_Status1_idx` (`state` ASC) VISIBLE,
  CONSTRAINT `FK_GAMESTATE_GAME`
    FOREIGN KEY (`game_id`)
    REFERENCES `community_game`.`game` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_GAMESTATE_USER`
    FOREIGN KEY (`user_id`)
    REFERENCES `community_game`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_GAMESTATE_STATE`
    FOREIGN KEY (`state`)
    REFERENCES `community_game`.`state` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into community_game.state(description) Values("BACKLOG"); 
insert into community_game.state(description) Values("PLAYING"); 
insert into community_game.state(description) Values("BEAT"); 
insert into community_game.state(description) Values("RETIRED"); 
