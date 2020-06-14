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
public class PromotionsProfileResponseDTO {
	
	private Integer itemCount;
	
	private String profileName;
	
	private Integer numberCustomers;
	
	private List<PromotionsProfileDTO> promotionsProfile;

}
