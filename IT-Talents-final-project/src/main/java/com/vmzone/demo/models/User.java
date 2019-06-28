package com.vmzone.demo.models;


import javax.persistence.*;

import com.vmzone.demo.enums.Gender;
import lombok.*;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String name;
  
	private String surname;
	
    private String email;
    
	private String password;

	@Enumerated(EnumType.STRING)
	private Gender gender;
    
	private int isAdmin;
    
	private int isSubscribed;
    
    private String phone; 
    private String city;
    private String postCode;
    private String adress;
    private int age;
    private boolean isDeleted;

	public User(String name, String surname, String email, String password, Gender gender,
			int isSubscribed, String phone, String city, String postCode, String adress, int age, boolean isDeleted) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.isSubscribed = isSubscribed;
		this.phone = phone;
		this.city = city;
		this.postCode = postCode;
		this.adress = adress;
		this.age = age;
		this.isDeleted = isDeleted;
	}

//	public User(Long id,String name, String surname, String email, String password, Gender gender,
//				int isSubscribed, String phone, String city, String postCode, String adress, int age, boolean isDeleted) {
//		super();
//		this.userId = id;
//		this.name = name;
//		this.surname = surname;
//		this.email = email;
//		this.password = password;
//		this.gender = gender;
//		this.isSubscribed = isSubscribed;
//		this.phone = phone;
//		this.city = city;
//		this.postCode = postCode;
//		this.adress = adress;
//		this.age = age;
//		this.isDeleted = isDeleted;
//	}


	public boolean isAdmin() {
		return this.getIsAdmin() == 1;
	}

}
