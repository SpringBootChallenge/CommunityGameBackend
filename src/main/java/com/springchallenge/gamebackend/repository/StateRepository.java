package com.springchallenge.gamebackend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springchallenge.gamebackend.model.State;

public interface StateRepository extends CrudRepository<State, Integer> {

    Optional<State> findByDescription(String description);
}
