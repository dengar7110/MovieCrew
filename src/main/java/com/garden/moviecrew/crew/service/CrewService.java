package com.garden.moviecrew.crew.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.dto.CrewView;
import com.garden.moviecrew.crew.repository.CrewRepository;
import com.garden.moviecrew.membership.domain.Membership;
import com.garden.moviecrew.membership.service.MembershipService;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

@Service
public class CrewService {

	private CrewRepository crewRepository;
	private UserService userService;
	private MembershipService membershipService;
	
	public CrewService(
			CrewRepository crewRepository
			, UserService userService
			, MembershipService membershipService) {
		this.crewRepository = crewRepository;
		this.userService = userService;
		this.membershipService = membershipService;
	}
	
	// crew 생성
	public Crew createCrew(int userId, String title, String description) {
		
		Crew crew = Crew.builder()
					.userId(userId)
					.title(title)
					.description(description)
					.build();
		
		Crew resultCrew = crewRepository.save(crew);

		return resultCrew;
	}
	
    public List<CrewView> getCrewViewList(int userId) {
    	
    	List<Crew> crewList = crewRepository.findAll();
    	
    	List<CrewView> crewViewList = new ArrayList<>();
    	
    	User user = userService.getUserById(userId);
    	
        for (Crew crew : crewList) {
            Membership membership = membershipService.getMembership(crew.getId(), user.getId());

            CrewView crewView = CrewView.builder()
                    .crewId(crew.getId())
                    .userId(user.getId())
                    .title(crew.getTitle())
                    .description(crew.getDescription())
                    .createdAt(crew.getCreatedAt())
                    .membershipStatus(membership != null ? membership.getStatus() : null)
                    .appliedAt(membership != null ? membership.getAppliedAt() : null)
                    .build();

            crewViewList.add(crewView);
        }

        return crewViewList;
    }
	
	public Crew getCrewById(int id) {
		
		return crewRepository.findById(id).orElse(null);
	}
	
	
}
