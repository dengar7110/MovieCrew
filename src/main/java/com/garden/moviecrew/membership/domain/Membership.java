package com.garden.moviecrew.membership.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="membership")
@Entity
public class Membership {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="crewId")
	private int crewId;
	
	@Column(name="userId")
	private int userId;
	
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private MembershipStatus status;
	
	@CreationTimestamp
	@Column(name="appliedAt")
	private LocalDateTime appliedAt;

}
