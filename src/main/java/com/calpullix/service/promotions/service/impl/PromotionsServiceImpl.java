package com.calpullix.service.promotions.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.calpullix.db.process.catalog.model.ProductStatus;
import com.calpullix.db.process.customer.model.CustomerProfile;
import com.calpullix.db.process.customer.repository.CustomerProfileRepository;
import com.calpullix.db.process.customer.repository.CustomerRepository;
import com.calpullix.db.process.product.model.ProductHistory;
import com.calpullix.db.process.product.repository.ProductBranchRepository;
import com.calpullix.db.process.product.repository.ProductHistoryRepository;
import com.calpullix.db.process.profile.model.ProfilePromotions;
import com.calpullix.db.process.profile.repository.ProfilePromotionsRepository;
import com.calpullix.db.process.promotions.model.Promotions;
import com.calpullix.db.process.promotions.repository.PromotionsRepository;
import com.calpullix.service.promotions.model.DetailPromotionsUpdateDTO;
import com.calpullix.service.promotions.model.ProfileDetailDTO;
import com.calpullix.service.promotions.model.ProfileDetailResponseDTO;
import com.calpullix.service.promotions.model.PromotionImageDTO;
import com.calpullix.service.promotions.model.PromotionImageResponseDTO;
import com.calpullix.service.promotions.model.PromotionsDetailDTO;
import com.calpullix.service.promotions.model.PromotionsDetailResponseDTO;
import com.calpullix.service.promotions.model.PromotionsProfileDTO;
import com.calpullix.service.promotions.model.PromotionsProfileRequest;
import com.calpullix.service.promotions.model.PromotionsProfileResponseDTO;
import com.calpullix.service.promotions.model.PromotionsRequestDTO;
import com.calpullix.service.promotions.repository.ProcedureInvoker;
import com.calpullix.service.promotions.service.PromotionsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PromotionsServiceImpl implements PromotionsService {

	private static final String PROFILE_LABEL = "Perfil ";

	private static final String PROCEDURE_PROFILE_PROMOTION_INFORMATION = "calpullix_profile_promotion_information";

	private static final String LABEL_TO = " a ";

	@Value("${app.pagination-size}")
	private Integer paginationSize;

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Autowired
	private ProfilePromotionsRepository profilePromotionsRepository;

	@Autowired
	private ProductBranchRepository productBranchRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProcedureInvoker procedureInvoker;

	@Autowired
	private PromotionsRepository promotionsRepository;

	@Autowired
	private ProductHistoryRepository productHistoryRepository;

	@Override
	public PromotionsProfileResponseDTO getAnalysisProductsClients(PromotionsRequestDTO request) {
		log.info(":: Service getAnalysisProductsClients {} ", request);
		final PromotionsProfileResponseDTO result = new PromotionsProfileResponseDTO();
		final List<CustomerProfile> customerProfileList = customerProfileRepository.findAllByActive(Boolean.TRUE);
		final Pageable pagination = PageRequest.of(0, paginationSize);
		final CustomerProfile idProfile = customerProfileList.get(request.getPage() - 1);
		final Page<ProfilePromotions> profilePromotions = profilePromotionsRepository
				.findAllByIdprofileAndActive(idProfile, Boolean.TRUE, pagination);
		final List<PromotionsProfileDTO> promotionsProfiles = new ArrayList<>();
		PromotionsProfileDTO item;
		for (final ProfilePromotions profilePromotion : profilePromotions) {
			item = new PromotionsProfileDTO();

			item.setPromotionDeadline(profilePromotion.getIdpromotion().getEnddate());
			item.setPromotionId(profilePromotion.getIdpromotion().getId());
			item.setNumberCustomers(
					productBranchRepository.getNumberCostumersByIdpromotion(profilePromotion.getIdpromotion()));
			item.setEarnings(productBranchRepository.getEariningsByIdpromotion(profilePromotion.getIdpromotion()));
			promotionsProfiles.add(item);
		}
		result.setPromotionsProfile(promotionsProfiles);
		result.setItemCount(customerProfileList.size());
		result.setProfileName(PROFILE_LABEL + idProfile.getName());
		result.setNumberCustomers(customerRepository.getNumberCustomersByIdprofile(idProfile));
		return result;
	}

	@Override
	public ProfileDetailResponseDTO getPromotionsProfile(PromotionsRequestDTO request) {
		log.info(":: Service getPromotionsProfile {} ", request);
		final ProfileDetailResponseDTO result = new ProfileDetailResponseDTO();
		final List<ProfileDetailDTO> profileDetail = new ArrayList<>();
		List<CompletableFuture<Boolean>> listResults = new ArrayList<>();
		final List<CustomerProfile> customerProfileList = customerProfileRepository.findAllByActive(Boolean.TRUE);
		Optional<Promotions> promotion;
		ProfileDetailDTO item;
		ProductHistory productHistory;
		for (final CustomerProfile customerProfile : customerProfileList) {
			item = new ProfileDetailDTO();
			item.setIdProfile(customerProfile.getId());
			item.setLabel(customerProfile.getName());
			listResults.add(procedureInvoker.executeProcedureBranchInformation(item, customerProfile.getId(),
					PROCEDURE_PROFILE_PROMOTION_INFORMATION));
			profileDetail.add(item);
		}
		CompletableFuture<?> completableFuture = CompletableFuture
				.allOf(listResults.toArray(new CompletableFuture[listResults.size()]));
		try {
			completableFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error(":: Error executing SP " + PROCEDURE_PROFILE_PROMOTION_INFORMATION, e);
		}
		for (final ProfileDetailDTO profileDto : profileDetail) {
			promotion = promotionsRepository.findById(profileDto.getPreferredPromotion());
			if (promotion.isPresent()) {
				profileDto.setPreferredItem(promotion.get().getIdproduct().getName() == null ? ""
						: promotion.get().getIdproduct().getName());
				productHistory = productHistoryRepository.findPurchasePriceByIdproductAndStatus(
						promotion.get().getIdproduct(), ProductStatus.ACTIVE.getId());
				profileDto.setSalePriceItem(
						productHistory.getSaleprice() == null ? BigDecimal.ZERO : productHistory.getSaleprice());
				profileDto.setPurchasePriceItem(productHistory.getPurchaseprice() == null ? BigDecimal.ZERO
						: productHistory.getPurchaseprice());
				profileDto
						.setLifePromotion(promotion.get().getCreationdate() + LABEL_TO + promotion.get().getEnddate());
				profileDto.setItemClassification(promotion.get().getIdproduct().getClassification() == null ? ""
						: promotion.get().getIdproduct().getClassification().getDescription());
				profileDto.setDepartment(promotion.get().getIdproduct().getCategory().getDescription() == null ? ""
						: promotion.get().getIdproduct().getCategory().getDescription());
			} else {
				profileDto.setPreferredItem("");
				profileDto.setSalePriceItem(BigDecimal.ZERO);
				profileDto.setLifePromotion("");
				profileDto.setItemClassification("");
				profileDto.setPurchasePriceItem(BigDecimal.ZERO);
				profileDto.setDepartment("");
			}
		}
		result.setProfileDetail(profileDetail);
		return result;
	}

	@Override
	public PromotionsDetailResponseDTO getDetailPromotion(PromotionsRequestDTO request) {
		log.info(":: Service getDetailPromotion {} ", request);
		final Optional<Promotions> promotion = promotionsRepository.findById(request.getIdPromotion());
		PromotionsDetailResponseDTO result;
		if (promotion.isPresent()) {
			result = new PromotionsDetailResponseDTO();
			final PromotionsDetailDTO detail = new PromotionsDetailDTO();
			detail.setAmountProfit(productBranchRepository.getAmountProfitByIdpromotion(promotion.get()));
			detail.setDescription(promotion.get().getDescription());
			detail.setDiscountPrice(promotion.get().getPricepromotion());
			detail.setId(promotion.get().getId());
			detail.setProduct(promotion.get().getName());
			detail.setDates(promotion.get().getCreationdate() + LABEL_TO + promotion.get().getEnddate());
			detail.setName(promotion.get().getName());
			final ProductHistory productHistory = productHistoryRepository.findPurchasePriceByIdproductAndStatus(
					promotion.get().getIdproduct(), ProductStatus.ACTIVE.getId());
			detail.setNetIncome(productHistory.getPurchaseprice().subtract(detail.getDiscountPrice()).setScale(2,
					RoundingMode.HALF_UP));
			detail.setOriginalPrice(productHistory.getSaleprice());
			detail.setPercentageDiscount(promotion.get().getPercentagediscount().multiply(BigDecimal.valueOf(100))
					.setScale(2, RoundingMode.HALF_UP));
			detail.setTaxes(promotion.get().getTaxes());
			detail.setVendor(promotion.get().getIdproduct().getProvider().getName());
			result.setResponse(detail);
		} else {
			result = null;
		}
		return result;
	}

	@Override
	public PromotionImageResponseDTO getPromotionsImages(PromotionsRequestDTO request) {
		log.info(":: Service getPromotionsImages {} ", request);
		final CustomerProfile idprofile = new CustomerProfile();
		idprofile.setId(request.getIdProfile());
		final Pageable pagination = PageRequest.of(request.getPage() - 1, paginationSize);
		final Page<ProfilePromotions> profilePromotions = profilePromotionsRepository
				.findAllByIdprofileAndActive(idprofile, Boolean.TRUE, pagination);
		final PromotionImageResponseDTO result = new PromotionImageResponseDTO();
		result.setItemCount(
				profilePromotionsRepository.getCountProfilePromotionsByIdProfileAndActive(idprofile, Boolean.TRUE));
		result.setIdProfile(idprofile.getId());
		List<PromotionImageDTO> images = new ArrayList<>();
		PromotionImageDTO image;
		for (final ProfilePromotions item : profilePromotions) {
			image = new PromotionImageDTO();
			image.setIdPromotion(item.getIdpromotion().getId());
			image.setImage(item.getIdpromotion().getImage());
			image.setSelected(item.getAccepted());
			image.setName(item.getIdpromotion().getName());
			images.add(image);
		}
		result.setPromotions(images);
		return result;
	}

	@Override
	public PromotionsProfileRequest updatePromotionsProfile(PromotionsRequestDTO request) {
		log.info(":: Service updatePromotionsProfile {} ", request);
		final PromotionsProfileRequest result = new PromotionsProfileRequest();
		result.setIsUpdate(Boolean.TRUE);
		List<ProfilePromotions> promotion;
		Promotions promotionModel;
		CustomerProfile profile;
		for (final DetailPromotionsUpdateDTO item : request.getPromotions().get(0).getDetail()) {
			promotionModel = new Promotions();
			promotionModel.setId(item.getIdPromotion());
			profile = new CustomerProfile();
			profile.setId(request.getIdProfile());
			promotion = profilePromotionsRepository.findByIdpromotionAndIdprofileAndActive(promotionModel, profile,
					Boolean.TRUE);
			if (BooleanUtils.negate(CollectionUtils.isEmpty(promotion))) {
				promotion.stream().forEach(prom -> {
					prom.setAccepted(item.getStatusPromotion());
					profilePromotionsRepository.saveAndFlush(prom);
				});
			}
		}
		return result;
	}

}
