package br.com.wine.store_service_region.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = ZipCodeRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface ZipCodeRangesCantBeInUse {

	String message() default "The zip code range you informed is in use!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
