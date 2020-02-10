package ftn.uns.ac.rs.ncandrej.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldDto {
	private String name;
	private Object value;
	private String type;
}
