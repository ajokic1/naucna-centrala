package ftn.uns.ac.rs.ncandrej.service.registration;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.model.UserRole;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Named("setUserAsReviewer")
public class SetUserAsReviewer implements JavaDelegate{
	
	@Autowired
	private ReviewerRepository reviewerRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Reviewer reviewer = reviewerRepo.findByUsername((String)delegateExecution.getVariable("username"));
		reviewer.setRole(UserRole.REVIEWER);
		reviewerRepo.save(reviewer);
		
		//TODO: Create Reviewer entity from user and save into ReviewerRepo
	}
}
