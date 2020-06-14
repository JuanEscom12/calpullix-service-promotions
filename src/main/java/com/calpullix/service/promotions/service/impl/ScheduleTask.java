package com.calpullix.service.promotions.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.calpullix.db.process.profile.model.ProfilePromotions;
import com.calpullix.db.process.profile.repository.ProfilePromotionsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleTask {

	private static final String SECHEDULING_EXPRESSION = "${app.scheduling-expression}";

	@Autowired
	private ProfilePromotionsRepository profilePromotionsRepository;

	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

	@Scheduled(cron = SECHEDULING_EXPRESSION)
	public void processPurchaseOrders() throws ParseException {
		log.info(":: Schedule promotions task started ");
		final List<ProfilePromotions> profilePromotions = profilePromotionsRepository.findAll();
		final Calendar currentDate = Calendar.getInstance();
		for (final ProfilePromotions item : profilePromotions) {
			if (formatDate.parse(item.getIdpromotion().getEnddate())
					.before(currentDate.getTime())) {
				item.setActive(Boolean.FALSE);
				profilePromotionsRepository.save(item);
				log.info(":: Profile promotion updated {} ", item);
			}
		}
	}
	
}
