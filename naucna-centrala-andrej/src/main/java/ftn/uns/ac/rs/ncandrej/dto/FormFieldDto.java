package ftn.uns.ac.rs.ncandrej.dto;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldDto {
	private String name;
	private VariableValueDto value;
}
