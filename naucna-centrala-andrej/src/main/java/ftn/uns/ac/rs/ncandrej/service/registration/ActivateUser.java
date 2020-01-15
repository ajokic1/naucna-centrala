package ftn.uns.ac.rs.ncandrej.service.registration;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Named("activateUser")
public class ActivateUser implements JavaDelegate{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		User user = userRepo.findByUsername((String)delegateExecution.getVariable("username"));
		String token = (String) delegateExecution.getVariable("emailToken");
		String trueToken = (String) delegateExecution.getVariable("emailValidationToken");
		
		if(token.equals(trueToken)) user.setVerified(true);
		userRepo.save(user);
		log.info("User " + user.getUsername() + " activated.");
	}
}
