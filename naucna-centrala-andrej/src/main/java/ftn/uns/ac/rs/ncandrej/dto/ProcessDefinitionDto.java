package ftn.uns.ac.rs.ncandrej.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDto {
	private String key;
	private String name;
	private String description;
}
