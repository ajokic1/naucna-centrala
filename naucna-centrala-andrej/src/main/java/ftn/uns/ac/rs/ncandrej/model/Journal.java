package ftn.uns.ac.rs.ncandrej.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="journal")
public class Journal implements Serializable{
	
	private static final long serialVersionUID = -5624392923146771463L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String issn;
	
	private String sellerUuid;
	
	@Column(name="open_access")
	private boolean openAccess;
	
	@ManyToMany(mappedBy = "journals")
	private List<ScientificField> fields;
	
	@OneToOne
	private Editor mainEditor;
	
	@OneToMany(mappedBy = "journal")
	private List<JournalEditor> fieldEditors;
	
	@ManyToMany(mappedBy = "journals")
	private List<Reviewer> reviewers;
	
	@OneToMany(mappedBy = "journal")
	private List<Paper> papers;
	
	@ManyToMany
	private List<User> subscriptions;
	
	private boolean active = false;
	
	private boolean sellerRegistered = false;
	
	
}
