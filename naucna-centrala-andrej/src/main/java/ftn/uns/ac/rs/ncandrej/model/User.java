package ftn.uns.ac.rs.ncandrej.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
		
	
	protected String username;
	
	protected String password;
	
	protected String email;
	
	@Column(name="first_name")
	protected String firstName;
	
	@Column(name="last_name")
	protected String lastName;
	
	protected String address;
	
	protected String city;
	
	protected String country;
	
	protected boolean verified;
	
	@Enumerated(EnumType.STRING)
	protected  UserRole role;
}
