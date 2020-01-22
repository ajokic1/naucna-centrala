package ftn.uns.ac.rs.ncandrej.dto;

import ftn.uns.ac.rs.ncandrej.model.PaperStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperResponseDto {
	private PaperStatus status;
	private String redirectUrl;
}
