package ftn.uns.ac.rs.ncandrej.model;



import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Editor extends User {
	
	@OneToOne(mappedBy="mainEditor")
	private Journal journal;
	
	@OneToOne
	private JournalEditor journalEditor;
	
}
