package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;

@Service
@Named("inputReviewerHandler")
public class InputReviewerHandler implements TaskListener{
	
	@Autowired
	ReviewerRepository reviewerRepo;
	
	@Autowired
	JournalRepository journalRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		String reviewer = (String) delegateExecution.getVariable("reviewer");
		ArrayList<String> reviewers = (ArrayList<String>) delegateExecution.getVariable("reviewers");
		reviewers.add(reviewer);
		
		Reviewer reviewerR = reviewerRepo.findByUsername(reviewer);
		Journal journal = journalRepo.findByIssn((String) delegateExecution.getVariable("issn"));
		journal.getReviewers().add(reviewerR);
		journalRepo.save(journal);
		
		delegateExecution.setVariable("reviewers", reviewers);
	}
}
