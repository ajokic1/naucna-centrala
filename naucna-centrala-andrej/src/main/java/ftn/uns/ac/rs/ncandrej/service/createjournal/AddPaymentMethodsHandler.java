package ftn.uns.ac.rs.ncandrej.service.createjournal;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.config.KpConfiguration;
import ftn.uns.ac.rs.ncandrej.dto.RedirectFieldDto;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.util.AES;

@Service
@Named("addPaymentMethodsHandler")
public class AddPaymentMethodsHandler implements TaskListener {
	
	@Autowired
	private KpConfiguration kpConfig;
	
	@Autowired
	JournalRepository journalRepo;
	
	@Value("${baseUrl}")
	String baseUrl;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		Journal journal = journalRepo.findByIssn((String) delegateExecution.getVariable("issn"));
		
		String redirectUrl = kpConfig.getUrls().get("choose_payment_front") 
				+ "/andrej"
				+ "/" + AES.encrypt(journal.getSellerUuid())
				+ "/" + delegateExecution.getProcessInstanceId()
				+ "/false";
		RedirectFieldDto redirect = new RedirectFieldDto(redirectUrl);
		delegateExecution.setVariable("paymentMethodsRedirectLink", redirect);
	}
}
