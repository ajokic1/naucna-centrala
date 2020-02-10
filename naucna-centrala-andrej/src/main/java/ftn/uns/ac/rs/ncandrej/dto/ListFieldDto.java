package ftn.uns.ac.rs.ncandrej.dto;

import java.io.Serializable;
import java.util.Map;

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.value.TypedValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFieldDto extends AbstractFormFieldType implements Serializable{
	
	private static final long serialVersionUID = -5304678499827910985L;
	
	private String selectedValue;
	private Map<String, String> options;
	
	@Override
	public String getName() {
		return "List";
	}
	@Override
	public TypedValue convertToFormValue(TypedValue propertyValue) {
		return propertyValue;
	}
	@Override
	public TypedValue convertToModelValue(TypedValue propertyValue) {
		// TODO Auto-generated method stub
		return propertyValue;
	}
	@Override
	public Object convertFormValueToModelValue(Object propertyValue) {
		// TODO Auto-generated method stub
		return propertyValue;
	}
	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		// TODO Auto-generated method stub
		return modelValue.toString();
	}
}
