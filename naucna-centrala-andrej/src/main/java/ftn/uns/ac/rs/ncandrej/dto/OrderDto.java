package ftn.uns.ac.rs.ncandrej.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private String sellerUuid;
	private String description;
	private String successUrl;
	private String failUrl;
}
