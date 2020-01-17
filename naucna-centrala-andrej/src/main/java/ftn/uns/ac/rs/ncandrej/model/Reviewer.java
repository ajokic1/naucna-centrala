package ftn.uns.ac.rs.ncandrej.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reviewer extends User {
	
	@ManyToMany
	private List<ScientificField> fields;
	
	@ManyToMany
	private List<Journal> journals;
	
	@OneToMany
	private List<Review> reviews;
}
