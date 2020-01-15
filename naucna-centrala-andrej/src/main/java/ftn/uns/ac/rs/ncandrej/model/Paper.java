package ftn.uns.ac.rs.ncandrej.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="paper")
public class Paper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String keywords;
	
	@Column(name = "pabstract")
	private String pAbstract;
	
	private String title;
	
	@ManyToOne
	private ScientificField field;
	
	@OneToMany
	private List<CoAuthor> coAuthors;
	
	@ManyToOne
	private User author;
	
	@ManyToMany
	private List<Reviewer> reviewers;
	
	@OneToMany
	private List<Review> reviews;
	
	private String DOI;
	
	
}
