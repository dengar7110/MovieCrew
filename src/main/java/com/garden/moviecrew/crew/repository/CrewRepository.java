package com.garden.moviecrew.crew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Integer> {

    List<Crew> findAllByOrderByIdDesc(); // 모든 크루 목록
    
    List<Crew> findByUserIdOrderByIdDesc(int userId); // 사용자별 크루 목록
    
    Crew findByIdAndUserId(int id, int userId); // 특정 크루
    
}
