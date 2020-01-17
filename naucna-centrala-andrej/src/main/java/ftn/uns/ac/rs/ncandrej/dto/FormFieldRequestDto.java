package ftn.uns.ac.rs.ncandrej.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldRequestDto {
	private String name;
	private String label;
	private String type;
	private List<String> values;
	private boolean required;
	private Map<String, String> properties;
	
	public FormFieldRequestDto (FormField formField) {
		this.name = formField.getId();
		this.label = formField.getLabel();
		this.type = formField.getTypeName();
		this.values = new ArrayList<>(); //TODO: enum values
		this.required = false;
		for(FormFieldValidationConstraint constraint: formField.getValidationConstraints()) {
			if(constraint.getName().equals("required")) this.required=true;
		}
		this.properties = formField.getProperties(); 
	}
}
