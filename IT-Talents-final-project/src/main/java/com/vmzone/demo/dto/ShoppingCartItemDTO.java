package com.vmzone.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingCartItemDTO {
	private Long product_id;
	private String title;
	private double price;
	private int quantity;

	@Override
	public boolean equals(Object obj) {
		ShoppingCartItemDTO sc = (ShoppingCartItemDTO) obj;
		return this.product_id.equals(sc.getProduct_id()) && this.title.equals(sc.title) && this.price == sc.getPrice()
				&& this.quantity == sc.getQuantity();
	}
}
