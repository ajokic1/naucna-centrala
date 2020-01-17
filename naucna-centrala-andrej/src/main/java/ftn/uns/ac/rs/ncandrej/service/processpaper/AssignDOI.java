package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.apache.commons.lang3.RandomStringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Paper;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.PaperRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;

@Service
@Named("assignDOI")
public class AssignDOI implements JavaDelegate {
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		User author = userRepo.findByUsername((String)delegateExecution.getVariable("initiator"));
		ScientificField field = fieldRepo.findByCode((String)delegateExecution.getVariable("field"));
		Paper paper = new Paper();
		paper.setAuthor(author);
		paper.setPAbstract((String)delegateExecution.getVariable("abstract"));
		paper.setField(field);
		paper.setTitle((String)delegateExecution.getVariable("title"));
		paper.setDOI(RandomStringUtils.randomAlphanumeric(10));
		paperRepo.save(paper);
		
	}
}
