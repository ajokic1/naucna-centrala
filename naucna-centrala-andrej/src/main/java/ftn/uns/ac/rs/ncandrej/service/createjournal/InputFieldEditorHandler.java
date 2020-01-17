package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.JournalEditor;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.JournalEditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@Service
@Named("inputFieldEditorHandler")
public class InputFieldEditorHandler implements TaskListener{
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Autowired
	EditorRepository editorRepo;
	
	@Autowired
	JournalEditorRepository journalEditorRepo;
	
	@Autowired
	JournalRepository journalRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		String editor = (String) delegateExecution.getVariable("editor");
		String editorField = (String) delegateExecution.getVariable("editorField");
		
		Journal journal = journalRepo.findByIssn((String)delegateExecution.getVariable("issn"));
		
		Editor e = editorRepo.findByUsername(editor);
		ScientificField field = fieldRepo.findByCode(editorField);
		
		JournalEditor journalEditor = new JournalEditor();
		journalEditor.setEditor(e);
		List<ScientificField> fields = new ArrayList<>();
		fields.add(field);
		journalEditor.setFields(fields);
		journalEditor.setJournal(journal);
		journalEditorRepo.save(journalEditor);
		
		ArrayList<String> editors = (ArrayList<String>) delegateExecution.getVariable("editors");
		editors.add(editor);
		delegateExecution.setVariable("editors", editors);
	}
}
