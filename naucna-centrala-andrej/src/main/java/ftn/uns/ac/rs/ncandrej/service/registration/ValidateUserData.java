package ftn.uns.ac.rs.ncandrej.service.registration;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ftn.uns.ac.rs.ncandrej.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Named("validateUserData")
public class ValidateUserData implements JavaDelegate{
	
	@Autowired
	private UserService userService;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		final String firstName = (String) delegateExecution.getVariable("firstName");
		final String lastName = (String) delegateExecution.getVariable("lastName");
		final String address = (String) delegateExecution.getVariable("address");
		final String city = (String) delegateExecution.getVariable("city");
		final String country = (String) delegateExecution.getVariable("country");
		final String title = (String) delegateExecution.getVariable("title");
		final String email = (String) delegateExecution.getVariable("email");
		final Boolean isReviewer = (Boolean) delegateExecution.getVariable("isReviewer");
		final String username = (String) delegateExecution.getVariable("username");
		final String password = (String) delegateExecution.getVariable("password");
		
		if(firstName == null || lastName==null || address==null || city==null || country==null || email==null
				|| username==null || password==null) {
			delegateExecution.setVariable("dataValid", false);
		} else {
			delegateExecution.setVariable("dataValid", true);
			userService.registerUser(username, password, email, firstName, lastName, address, city, country);
			System.out.println("Data for user " + username + " validated.");
		}		
	}
}
