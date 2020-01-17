package ftn.uns.ac.rs.ncandrej.dto;

import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScientificFieldDto {
	private String value;
	private String label;
	
	public ScientificFieldDto(ScientificField field) {
		this.value = field.getCode();
		this.label = field.getName();
	}
}
