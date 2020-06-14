package com.calpullix.service.promotions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailPromotionsUpdateDTO {

	private Integer idPromotion;
	
	private Boolean statusPromotion;
	
}
