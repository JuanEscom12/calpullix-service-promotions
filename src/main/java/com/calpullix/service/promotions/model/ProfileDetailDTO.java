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
public class ProfileDetailDTO {

	private Integer idProfile;
	
	private String label;
	
	private String customerType;
	
	private Integer age;
	
	private String state;
	
	private BigDecimal averagePromotions;
	
	private BigDecimal salePriceItem;
	
	private BigDecimal purchasePriceItem;
	
	private String lifePromotion;
	
	private String sex;
	
	private String maritalStatus;
	
	private Integer preferredPromotion;
	
	private String preferredItem;
	
	private String itemClassification;
	
	private String department;
	
	private String paymentMethod;
	
	private String bank;
	
}
