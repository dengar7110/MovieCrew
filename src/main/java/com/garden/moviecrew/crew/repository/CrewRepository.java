package com.garden.moviecrew.crew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Integer> {

}
