package ftn.uns.ac.rs.ncandrej.service.registration;

import javax.inject.Named;

import org.apache.commons.lang3.RandomStringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ftn.uns.ac.rs.ncandrej.util.MailService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Named("sendConfirmationEmail")
public class SendConfirmationEmail implements JavaDelegate{

	@Autowired
	MailService mailService;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		final String email = (String) delegateExecution.getVariable("email");
		final String token = RandomStringUtils.randomAlphanumeric(10);
		delegateExecution.setVariable("emailValidationToken", token);
		mailService.sendMail(email, "Naucna centrala - verifikacija mail-a", "Token za verifikaciju: " + token);
		log.info("Confirmation email sent to " + email + ".");
	}
}
