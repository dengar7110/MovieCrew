package com.garden.moviecrew.crew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Integer> {

	public List<Crew> findAllByOrderByIdDesc();
}
