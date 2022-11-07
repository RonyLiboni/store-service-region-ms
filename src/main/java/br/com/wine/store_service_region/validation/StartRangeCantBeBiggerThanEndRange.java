package br.com.wine.store_service_region.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = StartRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface StartRangeCantBeBiggerThanEndRange {

	String message() default "The start range can't be bigger than the end range!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
