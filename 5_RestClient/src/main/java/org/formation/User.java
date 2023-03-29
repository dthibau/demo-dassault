package org.formation;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private long id;
	private String email;
	private String password;
	private String nom, prenom;
	private int age;
	private Date registeredDate;

	
}
