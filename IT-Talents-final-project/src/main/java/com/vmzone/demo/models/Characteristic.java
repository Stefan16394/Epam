package com.vmzone.demo.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "characteristics")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Characteristic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long characteristicsId;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private String name;
	private String value;
	private boolean isDeleted;
	
	public Characteristic(Product product, String name, String value) {
		this.product = product;
		this.name = name;
		this.value = value;
	}

}
