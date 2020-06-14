package com.calpullix.service.promotions.repository;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.calpullix.db.process.catalog.model.State;
import com.calpullix.service.promotions.model.ProfileDetailDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProcedureInvoker {

	private static final String LABEL_FEMALE = ", Mujeres: ";
	
	private static final String LABEL_MALE = "Hombres: ";
	
	
	private final EntityManager entityManager;

	@Autowired
	public ProcedureInvoker(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Async
	public CompletableFuture<Boolean> executeProcedureBranchInformation(
			ProfileDetailDTO result,
			Integer idProfile, 
			String nameProcedure) {
    	log.info(":: Executing Procedure {} {} ", nameProcedure, idProfile);
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("id_profile", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("age", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("state", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("averagePromotions", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("sexMale", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("sexFemale", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("preferredPromotion", Integer.class, ParameterMode.INOUT);
     
        
        storedProcedureQuery.setParameter("id_profile", idProfile);
        storedProcedureQuery.setParameter("age", 0);
        storedProcedureQuery.setParameter("state", 0);
        storedProcedureQuery.setParameter("averagePromotions", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("sexMale", 0);
        storedProcedureQuery.setParameter("sexFemale", 0);
        storedProcedureQuery.setParameter("preferredPromotion", 0);
        storedProcedureQuery.execute();

        final Integer age = (Integer) storedProcedureQuery.getOutputParameterValue("age");
        final Integer  state = (Integer) storedProcedureQuery.getOutputParameterValue("state");
        final Integer  sexMale = (Integer) storedProcedureQuery.getOutputParameterValue("sexMale");
        final BigDecimal  averagePromotions = (BigDecimal) storedProcedureQuery.getOutputParameterValue("averagePromotions");  
        final Integer  sexFemale = (Integer) storedProcedureQuery.getOutputParameterValue("sexFemale");      
        final Integer  preferredPromotion = (Integer) storedProcedureQuery.getOutputParameterValue("preferredPromotion");    
     
        result.setAge(age == null ? 0: age);
        result.setState(state == null ? "" : State.of(state).getDescription());
        result.setAveragePromotions(averagePromotions == null ? BigDecimal.ZERO : averagePromotions);
        result.setSex(LABEL_MALE + (sexMale == null ? 0 : sexMale) + LABEL_FEMALE + (sexFemale == null ? 0 : sexFemale));
        result.setPreferredPromotion(preferredPromotion == null ? 0 : preferredPromotion);
        
        log.info(":: Result SP: {} ", result);
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }


}
