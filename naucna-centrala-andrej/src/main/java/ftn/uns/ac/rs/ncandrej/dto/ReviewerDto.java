package ftn.uns.ac.rs.ncandrej.dto;

import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewerDto {
	private String value;
	private String label;
	
	public ReviewerDto (Reviewer reviewer) {
		this.value = reviewer.getUsername();
		this.label = reviewer.getFirstName() + " " + reviewer.getLastName();
	}
}
