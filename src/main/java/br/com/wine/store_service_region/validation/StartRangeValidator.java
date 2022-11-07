package br.com.wine.store_service_region.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.com.wine.store_service_region.dto.NewZipCodeRangeRequest;

public class StartRangeValidator implements ConstraintValidator<StartRangeCantBeBiggerThanEndRange, NewZipCodeRangeRequest> {

	@Override
	public boolean isValid(NewZipCodeRangeRequest newZipCodeRange, ConstraintValidatorContext context) {
		
		if (newZipCodeRange.getStartRange() > newZipCodeRange.getEndRange()) {
			return false;
		}
		
		return true;
	}

}
