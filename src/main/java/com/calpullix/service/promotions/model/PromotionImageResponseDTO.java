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
public class PromotionImageResponseDTO {
	
	private Integer itemCount;
	
	private Integer idProfile;
	
	private List<PromotionImageDTO> promotions;

}
