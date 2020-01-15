package ftn.uns.ac.rs.ncandrej.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDataDto {
	private String taskId;
    private List<FormFieldDto> fields;

}
