package ftn.uns.ac.rs.ncandrej.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.ReviewerDto;
import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;

@RestController
@RequestMapping("/reviewers")
public class ReviewerController {
	
	@Autowired
	ReviewerRepository reviewerRepo;
	
	@GetMapping
	public List<ReviewerDto> getReviewers() {
		List<Reviewer> reviewers = reviewerRepo.findAll();
		List<ReviewerDto> reviewerDtos = new ArrayList<>();
		
		for(Reviewer reviewer: reviewers) {
			reviewerDtos.add(new ReviewerDto(reviewer));
		}
		
		return reviewerDtos;
	}
}
