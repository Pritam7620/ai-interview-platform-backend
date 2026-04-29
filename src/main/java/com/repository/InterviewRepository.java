package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Interview;
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
	
	
}



