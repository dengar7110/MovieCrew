package com.garden.moviecrew.crew.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.crew.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Integer> {

    List<Crew> findAllByOrderByIdDesc(); // 모든 크루 목록
    
    Optional<Crew> findById(int id); // 특정 크루
    
    //나중에 확인
    List<Crew> findByUserIdOrderByIdDesc(int userId); // 사용자별 크루 목록
    
}
