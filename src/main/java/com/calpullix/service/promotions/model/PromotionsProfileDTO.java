package com.calpullix.service.promotions.model;

import java.math.BigDecimal;

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
public class PromotionsProfileDTO {

	private Integer promotionId;
	
	private Integer idProfile;
	
	private Integer numberCustomers;
	
	private BigDecimal earnings;
	
	private String promotionDeadline;
	
}
