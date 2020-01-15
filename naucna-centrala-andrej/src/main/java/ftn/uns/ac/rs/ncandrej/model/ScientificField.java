package ftn.uns.ac.rs.ncandrej.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="scientific_field")
public class ScientificField {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String code;
	
	private String name;
	
	@ManyToMany
	private List<Journal> journals;
	
	@ManyToMany(mappedBy = "fields")
	private List<JournalEditor> journalEditors;
	
	@ManyToMany(mappedBy = "fields")
	private List<Reviewer> reviewers;
}
