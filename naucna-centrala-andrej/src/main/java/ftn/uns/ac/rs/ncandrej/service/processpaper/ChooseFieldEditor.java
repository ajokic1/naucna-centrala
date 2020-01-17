package ftn.uns.ac.rs.ncandrej.service.processpaper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.JournalEditor;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@Service
@Named("chooseFieldEditor")
public class ChooseFieldEditor implements JavaDelegate {
	
	@Autowired
	JournalRepository journalRepo;
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		List<JournalEditor> fieldEditors = (ArrayList<JournalEditor>) delegateExecution.getVariable("fieldEditors");
		ScientificField field = fieldRepo.findByCode((String) delegateExecution.getVariable("field"));
		
		String fieldEditor= (String) delegateExecution.getVariable("mainEditor");
		
		for(JournalEditor editor: fieldEditors) {
			if(editor.getFields().contains(field))
				fieldEditor = editor.getEditor().getUsername(); 
		}
		
		delegateExecution.setVariable("field_editor", fieldEditor);
	}
}
