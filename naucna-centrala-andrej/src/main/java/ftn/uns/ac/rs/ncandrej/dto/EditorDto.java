package ftn.uns.ac.rs.ncandrej.dto;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorDto {
	private String value;
	private String label;
	
	public EditorDto(Editor editor) {
		this.value = editor.getUsername();
		this.label = editor.getFirstName() + " " + editor.getLastName();
	}
}
