package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;

@Service
@Named("checkMembership")
public class CheckMembership implements JavaDelegate{
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		User user = userRepo.findByUsername((String) delegateExecution.getVariable("initiator"));
		delegateExecution.setVariable("hasMembership", user.isMember());
	}
}
