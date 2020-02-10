package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ftn.uns.ac.rs.ncandrej.config.KpConfiguration;
import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@Service
@Named("saveJournalData")
public class SaveJournalData implements JavaDelegate {
	
	@Autowired
	JournalRepository journalRepo;
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Autowired
	EditorRepository editorRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	KpConfiguration kpConfig;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		String name = (String) delegateExecution.getVariable("name");
		String issn = (String) delegateExecution.getVariable("issn");
		Boolean openAccess = (Boolean) delegateExecution.getVariable("openAccess");
		Editor mainEditor = editorRepo.findByUsername((String) delegateExecution.getVariable("initiator"));
		
		ArrayList<String> fields = (ArrayList<String>) delegateExecution.getVariable("fields");
		ArrayList<ScientificField> scFields = new ArrayList<>();
		for(String field: fields) {
			scFields.add(fieldRepo.findByCode(field));
		}
		
		Journal journal = new Journal();
		journal.setName(name);
		journal.setIssn(issn);
		journal.setSellerUuid(UUID.randomUUID().toString());
		journal.setOpenAccess(openAccess);
		journal.setFields(scFields);
		journal.setMainEditor(mainEditor);
		
		//registerInKp(journal);
		journalRepo.save(journal);		
	}	
	
	@Async
	private void registerInKp(Journal journal) {
		Map<String,Object> request = new HashMap<>();
		List<String> paymentMethods = Arrays.asList(restTemplate.getForEntity("", String[].class).getBody());
		
		request.put("email", journal.getMainEditor().getEmail());
		//request.put("paymentMethods", paymentMethods);
		request.put("uuid", journal.getSellerUuid());
		request.put("amount", 5);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = restTemplate
				.postForEntity(kpConfig.getUrls().get("registration"), request, Map.class)
				.getBody();
		if (((String)result.get("uuid")).equals(journal.getSellerUuid()))
			journal.setSellerRegistered(true);		
	}
}
