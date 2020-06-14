package com.calpullix.service.promotions.model;

import java.util.List;

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
public class PromotionsProfileRequest {
	
	private Integer idCustomerType;
	
	private Integer idPromotion;
	
	private Integer page;
	
	private List<Integer> promotions;
	
	private Boolean isUpdate;
	
	private String date;
	
}
