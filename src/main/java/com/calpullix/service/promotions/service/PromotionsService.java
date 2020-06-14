package com.calpullix.service.promotions.service;

import com.calpullix.service.promotions.model.ProfileDetailResponseDTO;
import com.calpullix.service.promotions.model.PromotionImageResponseDTO;
import com.calpullix.service.promotions.model.PromotionsDetailResponseDTO;
import com.calpullix.service.promotions.model.PromotionsProfileRequest;
import com.calpullix.service.promotions.model.PromotionsProfileResponseDTO;
import com.calpullix.service.promotions.model.PromotionsRequestDTO;

public interface PromotionsService {
	
	PromotionsProfileResponseDTO getAnalysisProductsClients(PromotionsRequestDTO request);

	ProfileDetailResponseDTO getPromotionsProfile(PromotionsRequestDTO request);
	
	PromotionsDetailResponseDTO getDetailPromotion(PromotionsRequestDTO request);
	
	PromotionImageResponseDTO getPromotionsImages(PromotionsRequestDTO request);
	
	PromotionsProfileRequest updatePromotionsProfile(PromotionsRequestDTO request);
	
}
