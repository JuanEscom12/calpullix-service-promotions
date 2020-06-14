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
public class PromotionImageDTO {
	
	private Integer idPromotion;
	
	private boolean selected;
	
	private String name;
	
	private byte[] image;

}
