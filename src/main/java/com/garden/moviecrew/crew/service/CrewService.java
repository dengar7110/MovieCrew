package com.garden.moviecrew.crew.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.garden.moviecrew.common.FileManager;
import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.dto.CrewView;
import com.garden.moviecrew.crew.repository.CrewRepository;
import com.garden.moviecrew.membership.domain.Membership;
import com.garden.moviecrew.membership.repository.MembershipRepository;
import com.garden.moviecrew.membership.service.MembershipService;
import com.garden.moviecrew.post.service.PostService;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

@Service
public class CrewService {

	private CrewRepository crewRepository;
	private UserService userService;
	private PostService postService;
	private MembershipService membershipService;
	private MembershipRepository membershipRepository;
	
	public CrewService(
			CrewRepository crewRepository
			, UserService userService
			, PostService postService
			, MembershipService membershipService
			, MembershipRepository membershipRepository) {
		this.crewRepository = crewRepository;
		this.userService = userService;
		this.postService = postService;
		this.membershipService = membershipService;
		this.membershipRepository = membershipRepository;
	}
	
	// crew 생성
	public Crew createCrew(int userId, String title, String description, MultipartFile file) {
		
		String urlPath = FileManager.saveFile(userId, file);
		
		Crew crew = Crew.builder()
					.userId(userId)
					.title(title)
					.description(description)
					.imagePath(urlPath)
					.build();
		
		Crew resultCrew = crewRepository.save(crew);

		Membership membership = membershipService.requestMembership(resultCrew.getId(), userId);
		
		membershipRepository.save(membership);
		
		membershipService.approveMembership(resultCrew.getId(), userId);
		
		return resultCrew;
	}
	
	// CrewView 리스트 조회
    public List<CrewView> getCrewViewList(int userId) {
    	
    	List<Crew> crewList = crewRepository.findAll();
    	
    	List<CrewView> crewViewList = new ArrayList<>();
    	
    	User user = userService.getUserById(userId);
    	
        for (Crew crew : crewList) {
        	
            Membership membership = membershipService.getMembership(crew.getId(), user.getId());

            User creator = userService.getUserById(crew.getUserId());
            
            CrewView crewView = CrewView.builder()
                    .crewId(crew.getId())
                    .userId(creator.getId())
                    .title(crew.getTitle())
                    .description(crew.getDescription())
                    .imagePath(crew.getImagePath())
                    .createdAt(crew.getCreatedAt())
                    .status(membership != null ? membership.getStatus().name() : null)
                    .creator(creator != null ? creator.getNickName() : null)
                    .build();

            crewViewList.add(crewView);
        }

        return crewViewList;
    }
	
	public Crew getCrewById(int crewId) {
		
		return crewRepository.findById(crewId).orElse(null);
	}
	
	// Crew 정보 수정하기
	public Crew editCrewByCrewId(int crewId, int userId, String title, String description, MultipartFile file) {
		
		 Crew crew = getCrewById(crewId);
		 
		 FileManager.removeFile(crew.getImagePath());
		 
		 String urlPath = FileManager.saveFile(userId, file);
		 
		 if(crew != null) {
			 crew.setTitle(title);
			 crew.setDescription(description);
			 crew.setImagePath(urlPath);
			 crew.setUpdatedAt(LocalDateTime.now());
			 
			 crewRepository.save(crew);
		 } 
		 
		 return crew;
	}
	
	
	// Crew 삭제하기
	public boolean deleteCrew(int crewId, int userId) {
		
		Crew crew = crewRepository.findById(crewId).orElse(null);
		
		if(crew != null && crew.getUserId() == userId) {
			// Crew 에 관련된 모든 게시글과 댓글 삭제 
			postService.deletePostByCrewId(crewId);
			// Crew 에 과련된 멤버십 삭제
			membershipService.deleteMembershipByCrewId(crewId);
			// Crew 삭제
			crewRepository.delete(crew);
			return true;
		} else {
			return false;
		}
	}
	
	
}