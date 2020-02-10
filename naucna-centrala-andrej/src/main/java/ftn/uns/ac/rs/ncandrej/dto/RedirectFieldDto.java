package ftn.uns.ac.rs.ncandrej.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectFieldDto implements Serializable{
	private static final long serialVersionUID = 8650457127242089608L;
	private String url;
}
