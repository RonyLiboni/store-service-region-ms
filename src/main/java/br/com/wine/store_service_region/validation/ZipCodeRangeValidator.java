package br.com.wine.store_service_region.validation;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;
import br.com.wine.store_service_region.dto.NewZipCodeRangeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZipCodeRangeValidator implements ConstraintValidator<ZipCodeRangesCantBeInUse, NewZipCodeRangeRequest> {

	private final EntityManager entityManager;

	@Override
	public boolean isValid(NewZipCodeRangeRequest newZipCodeRange, ConstraintValidatorContext context) {
		Assert.notNull(entityManager, "The entity manager should not be null");
		String jpql = "select count(1) > 0 from ZipCodeRange z where "
															+ "z.startRange between :startRange and :endRange "
															+ "or "
															+ "z.endRange between :startRange and :endRange ";
		
		boolean isStartOrEndRangesInUse = entityManager
									.createQuery(jpql, Boolean.class)
									.setParameter("startRange", newZipCodeRange.getStartRange())
									.setParameter("endRange", newZipCodeRange.getEndRange())
									.getSingleResult();
		
		
		return !isStartOrEndRangesInUse;
	}

}
