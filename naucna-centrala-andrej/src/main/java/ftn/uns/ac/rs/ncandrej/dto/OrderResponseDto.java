package ftn.uns.ac.rs.ncandrej.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
	private String transactionId;
	private List<String> paymentMethods;
	private String uuid;
	private double amount;
}
