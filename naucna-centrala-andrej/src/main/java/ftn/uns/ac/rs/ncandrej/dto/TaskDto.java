package ftn.uns.ac.rs.ncandrej.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
	private String id;
	private String processId;
	private String name;
	private String assignee;
}
