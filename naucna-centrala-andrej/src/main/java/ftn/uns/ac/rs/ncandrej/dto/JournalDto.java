package ftn.uns.ac.rs.ncandrej.dto;

import java.util.ArrayList;
import java.util.List;

import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDto {
	private long id;
	private String name;
	private String issn;
	private String editor;
	private boolean openAccess;
	private List<String> fields;
	
	
	public JournalDto(Journal journal) {
		id = journal.getId();
		name = journal.getName();
		issn = journal.getIssn();
		openAccess = journal.isOpenAccess();
		fields = new ArrayList<>();
		editor = journal.getMainEditor().getFirstName() + " " + journal.getMainEditor().getLastName();
		for(ScientificField field: journal.getFields()) {
			fields.add(field.getCode());
		}
	}
}
