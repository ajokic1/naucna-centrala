package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.DelegationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.PaperSuggestion;
import ftn.uns.ac.rs.ncandrej.model.Review;
import ftn.uns.ac.rs.ncandrej.repository.ReviewRepository;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;

@Service
@Named("submitReview")
public class SubmitReview implements JavaDelegate {
	
	@Autowired
	ReviewerRepository reviewerRepo;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Review review = new Review();
		review.setComment((String) delegateExecution.getVariable("comment"));
		review.setCommentToEditor((String) delegateExecution.getVariable("commentToEditor"));
		review.setSuggestion((PaperSuggestion) delegateExecution.getVariable("suggestion"));
		review.setReviewer(reviewerRepo.findByUsername((String)delegateExecution.getVariable("fieldReviewer")));
		reviewRepo.save(review);
	}
}
