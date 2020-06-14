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
public class PromotionsPayloadDTO {
	
	private List<DetailPromotionsUpdateDTO> detail;

}
