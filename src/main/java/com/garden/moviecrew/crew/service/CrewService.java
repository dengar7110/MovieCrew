package com.garden.moviecrew.crew.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.repository.CrewRepository;

@Service
public class CrewService {

	private CrewRepository crewRepository;
	
	public CrewService(CrewRepository crewRepository) {
		this.crewRepository = crewRepository;
	}
	
	// crew 생성
	public Crew addCrew(int userId, String title, String description) {
		
		Crew crew = Crew.builder()
					.userId(userId)
					.title(title)
					.description(description)
					.build();
		
		Crew result = crewRepository.save(crew);
		
		return result;
	}
	
    public List<Crew> getAllCrews() {
    	
        return crewRepository.findAll(); // 모든 크루를 가져옴
    }
	
	public Crew getCrewById(int id) {
		
		return crewRepository.findById(id).orElse(null);
	}
	
	
}
